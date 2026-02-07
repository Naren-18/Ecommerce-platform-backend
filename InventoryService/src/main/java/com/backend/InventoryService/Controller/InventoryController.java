package com.backend.InventoryService.Controller;

import com.backend.InventoryService.Model.Dto.InitInventoryRequest;
import com.backend.InventoryService.Model.Dto.InventoryResponse;
import com.backend.InventoryService.Model.Dto.QuantityRequest;
import com.backend.InventoryService.Service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    //Initialize
    @PostMapping("/{productId}/init")
    public ResponseEntity<String> initialize(@PathVariable UUID productId, @RequestBody @Valid InitInventoryRequest initInventoryRequest){
         inventoryService.initialize(productId,initInventoryRequest);
         return ResponseEntity.status(HttpStatus.CREATED).body("initialized the quantity");
    }

    //Add the Quantity
    @PostMapping("/{productId}/add")
    public ResponseEntity<String> addStock(@PathVariable UUID productId, @RequestBody @Valid QuantityRequest quantityRequest){
         inventoryService.addStock(productId,quantityRequest);
        return ResponseEntity.ok("Stock Added");
    }

    //Get Quantity by Id
    @GetMapping("/{productId}/quantity")
    public ResponseEntity<InventoryResponse> getQuantityById(@PathVariable UUID productId){
        return ResponseEntity.ok(inventoryService.getQuantityById(productId));
    }

    //Get All the inventory items
    @GetMapping("/quantity")
    public  ResponseEntity<List<InventoryResponse>> getAllQuantity(){
        return ResponseEntity.ok(inventoryService.getAllQuantity());
    }

    //Reserve the quantity
    @PostMapping("/{productId}/reserve")
    public ResponseEntity<String> reserve(@PathVariable UUID productId, @RequestBody @Valid QuantityRequest quantityRequest){
        inventoryService.reserve(productId,quantityRequest);
        return ResponseEntity.ok("reserved the stock ");
    }

    //Release the quantity
    @PostMapping("/{productId}/release")
    public ResponseEntity<String> release(@PathVariable UUID productId, @RequestBody @Valid QuantityRequest quantityRequest){
        inventoryService.release(productId,quantityRequest);
        return ResponseEntity.ok("Reservation is Undone");
    }

    //Commit the quantity
    @PostMapping("/{productId}/commit")
    public ResponseEntity<String> commit(@PathVariable UUID productId, @RequestBody @Valid QuantityRequest quantityRequest){
        inventoryService.commit(productId,quantityRequest);
        return ResponseEntity.ok("Commit done successfully");
    }

}
