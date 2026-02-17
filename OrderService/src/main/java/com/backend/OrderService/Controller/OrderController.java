package com.backend.OrderService.Controller;

import com.backend.OrderService.Model.Dto.OrderInfoResponse;
import com.backend.OrderService.Model.Dto.OrderRequest;
import com.backend.OrderService.Model.Dto.OrderResponse;
import com.backend.OrderService.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody @Valid OrderRequest orderRequest)
    {
        orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed Successfully");
    }

    //Get the Orders By Id
    @GetMapping("/{orderId}/orderDetails")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID orderId)
    {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    //Get the orders by Id for the payment service
    @GetMapping("/{orderId}/order")
    public ResponseEntity<OrderInfoResponse> getOrder(@PathVariable UUID orderId)
    {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable UUID orderId)
    {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order Cancelled");
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable UUID orderId)
    {
        orderService.confirm(orderId);
        return ResponseEntity.ok("Order Confirmed");
    }
}
