package com.backend.OrderService.Service;

import com.backend.OrderService.Client.Dto.ProductPriceResponse;
import com.backend.OrderService.Client.Dto.QuantityRequest;
import com.backend.OrderService.Client.InventoryClient;
import com.backend.OrderService.Client.ProductClient;
import com.backend.OrderService.Exception.OrderFailedException;
import com.backend.OrderService.Exception.OrderNotFoundException;
import com.backend.OrderService.Exception.OrderStateException;
import com.backend.OrderService.Model.Dto.OrderItemRequest;
import com.backend.OrderService.Model.Dto.OrderRequest;
import com.backend.OrderService.Model.OrderItem;
import com.backend.OrderService.Model.OrderStatus;
import com.backend.OrderService.Model.Orders;
import com.backend.OrderService.Repo.OrderRepo;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private InventoryClient inventoryClient;
    @Autowired
    private ProductClient productClient;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) throws OrderFailedException {

        Orders order = new Orders();
        order.setUserId(orderRequest.getUserId());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal TotalAmount = BigDecimal.ZERO;
        String currency = null;

        for(OrderItemRequest orderItemRequest : orderRequest.getItems())
        {
            //Calling the Product service to get price of the product
            ProductPriceResponse priceResponse = productClient.getProductPriceById(orderItemRequest.getProductId());
           if (currency==null)
           {
               currency = priceResponse.getCurrency();
           }
           else if (!currency.equals(priceResponse.getCurrency()))
           {
              throw new OrderFailedException("Multiple currencies not supported");
           }
           //The below logic calculates the individual total item price
           BigDecimal itemTotal =
                   priceResponse.getPrice()
                           .multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()));
           //This adds the total amount of the order
           TotalAmount = TotalAmount.add(itemTotal);

            //Creating OrderItem from the OrderItemRequest;
            OrderItem orderItem = OrderItem.builder()
                    .productId(orderItemRequest.getProductId())
                    .quantity(orderItemRequest.getQuantity())
                    .unitPrice(priceResponse.getPrice())
                    .order(order)
                    .build();

            orderItems.add(orderItem);
        }
        order.setTotalAmount(TotalAmount);
        order.setCurrency(currency);
        order.setOrderItems(orderItems);
        orderRepo.save(order);

        List<OrderItem> reservedSoFar = new ArrayList<>();
        try
        {
            for (OrderItem item : orderItems)
            {
                inventoryClient.reserve(
                        item.getProductId(),new QuantityRequest(item.getQuantity())
                );
                reservedSoFar.add(item);
            }
            order.setStatus(OrderStatus.RESERVED);
            orderRepo.save(order);
        }
        catch (FeignException Fe)
        {
                for (OrderItem item : reservedSoFar)
                {
                    try
                    {
                        inventoryClient.release(
                                item.getProductId(), new QuantityRequest(item.getQuantity())
                        );

                    }catch (Exception e)
                    {
                        log.error("Cirtical : Failed to release the item from the inventory.Manual check s required! OrderItemId:{},Error:{}",item.getOrderItemId(),e.getMessage());
                    }
                }


            order.setStatus(OrderStatus.FAILED);
            orderRepo.save(order);
            throw new OrderFailedException("Order failed during inventory reservation");

        }
    }

    @Transactional
    public void     cancelOrder(UUID orderId)
    {
        Orders order = orderRepo.findById(orderId).orElseThrow(
                ()-> new OrderNotFoundException("Order Not Found")
        );

        if (order.getStatus() == OrderStatus.CREATED )
        {
            order.setStatus(OrderStatus.CANCELLED);
        }
        else if(order.getStatus()==OrderStatus.RESERVED)
        {
            List<OrderItem> items = order.getOrderItems();
                for (OrderItem item: items)
                {
                    try
                    {
                        inventoryClient.release(
                                item.getProductId(),new QuantityRequest(item.getQuantity())
                        );
                    }catch (Exception e)
                    {
                        log.error("Critical : Failed to release the item from the inventory.Manual check s required! OrderItemId:{},Error:{}",item.getOrderItemId(),e.getMessage());
                    }

                }
            order.setStatus(OrderStatus.CANCELLED);
        }
        else
        {
            throw new OrderStateException("Check the Order Status");
        }
        orderRepo.save(order);
    }

    @Transactional
    public void confirm(UUID orderId)
    {
        Orders order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order Not Found"));

        if (order.getStatus()==OrderStatus.RESERVED)
        {
            try
            {
                for (OrderItem item : order.getOrderItems())
                {
                    inventoryClient.commit(
                        item.getProductId(),new QuantityRequest(item.getQuantity())
                );
                }
            }
            catch(FeignException fe)
            {
                throw new OrderStateException("Confirm failed during inventory commit. Please retry.");
            }
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepo.save(order);
        }
        else
        {
            throw new OrderStateException("Only RESERVED orders can be confirmed");
        }
    }
}
