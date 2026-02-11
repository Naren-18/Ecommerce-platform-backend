package com.backend.OrderService.Client;


import com.backend.OrderService.Client.Dto.QuantityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "INVENTORYSERVICE",path = "/inventory")
public interface InventoryClient {

    //Reserve the quantity
    @PostMapping("/{productId}/reserve")
    public String reserve(@PathVariable UUID productId, @RequestBody QuantityRequest quantityRequest);

    //Release the quantity
    @PostMapping("/{productId}/release")
    public String release(@PathVariable UUID productId, @RequestBody QuantityRequest quantityRequest);

    //Commit the quantity
    @PostMapping("/{productId}/commit")
    public String commit(@PathVariable UUID productId, @RequestBody QuantityRequest quantityRequest);

}
