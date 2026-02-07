package com.backend.InventoryService.Service;

import com.backend.InventoryService.Exception.InsufficientStockException;
import com.backend.InventoryService.Exception.ProductExistsException;
import com.backend.InventoryService.Exception.ProductNotFoundException;
import com.backend.InventoryService.Model.Dto.InitInventoryRequest;
import com.backend.InventoryService.Model.Dto.InventoryResponse;
import com.backend.InventoryService.Model.Dto.QuantityRequest;
import com.backend.InventoryService.Model.InventoryItem;
import com.backend.InventoryService.Repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    //Creating a new inventory item
    @Transactional
    public void initialize(UUID productId, InitInventoryRequest initInventoryRequest) {
        Optional<InventoryItem> item = inventoryRepo.findById(productId);
        if(item.isEmpty())
        {
                InventoryItem newItem = InventoryItem.builder()
                        .productId(productId)
                        .availableQuantity(initInventoryRequest.getInitialQuantity())
                        .build();

                inventoryRepo.save(newItem);

        }
        else
        {
            throw new ProductExistsException("Product exists");
        }
    }


    //Getting the particular quantity by Id
    public InventoryResponse getQuantityById(UUID productId) {
        InventoryItem existingItem = inventoryRepo.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

            InventoryResponse inventoryResponse = InventoryResponse.builder()
                    .productId(existingItem.getProductId())
                    .availableQuantity(existingItem.getAvailableQuantity())
                    .reservedQuantity(existingItem.getReservedQuantity())
                    .updatedAt(existingItem.getUpdatedAt())
                    .build();
            return inventoryResponse;

    }

    //All the items in the repo
    public List<InventoryResponse> getAllQuantity() {

        List<InventoryResponse> inventoryResponses = inventoryRepo.findAll().stream().map(Item -> InventoryResponse.builder()
                .productId(Item.getProductId())
                .availableQuantity(Item.getAvailableQuantity())
                .reservedQuantity(Item.getReservedQuantity())
                .updatedAt(Item.getUpdatedAt())
                .build()).toList();

        return inventoryResponses;

    }

    //Updating or Adding the stock of the existing item
    @Transactional
    public void addStock(UUID productId, QuantityRequest quantityRequest) {
        InventoryItem existingItem = inventoryRepo.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

            int availableQty = existingItem.getAvailableQuantity();

                availableQty += quantityRequest.getQuantity();
                existingItem.setAvailableQuantity(availableQty);
                inventoryRepo.save(existingItem);

    }

    //To reserve an item
    @Transactional
    public void reserve(UUID productId, QuantityRequest quantityRequest) {

        InventoryItem existingItem = inventoryRepo.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

            int availableQty = existingItem.getAvailableQuantity();
            int reservedQty = existingItem.getReservedQuantity();
            int qty = quantityRequest.getQuantity();

                if (availableQty >= qty)
                {
                    availableQty -= qty;
                    reservedQty += qty;
                    existingItem.setAvailableQuantity(availableQty);
                    existingItem.setReservedQuantity(reservedQty);
                }
                else
                    throw new InsufficientStockException("Insufficient Stock");

    }

    //Undo a reservation
    @Transactional
    public void release(UUID productId, QuantityRequest quantityRequest) {

        InventoryItem existingItem = inventoryRepo.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

            int availableQty = existingItem.getAvailableQuantity();
            int reservedQty = existingItem.getReservedQuantity();
            int qty = quantityRequest.getQuantity();

                if (reservedQty >= qty)
                {
                    reservedQty -= qty;
                    availableQty += qty ;

                    existingItem.setAvailableQuantity(availableQty);
                    existingItem.setReservedQuantity(reservedQty);
                }
                else
                    throw new InsufficientStockException("Insufficient reserved stock");


    }

    //The order is placed successfully and the reservedQty should be back to normal
    @Transactional
    public void commit(UUID productId, QuantityRequest quantityRequest) {

        InventoryItem existingItem = inventoryRepo.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

            int reservedQty = existingItem.getReservedQuantity();
            int qty = quantityRequest.getQuantity();

                if(reservedQty >= qty)
                {
                    reservedQty -= qty;
                    existingItem.setReservedQuantity(reservedQty);
                }
                else
                    throw new InsufficientStockException("Insufficient reserved stock during commit");
        }

}
