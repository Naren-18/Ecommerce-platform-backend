package com.backend.OrderService.Client;

import com.backend.OrderService.Client.Dto.ProductPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "PRODUCTSERVICE",path = "/api")
public interface ProductClient {

    @GetMapping("/product/{productId}/price")
    public ProductPriceResponse getProductPriceById(@PathVariable UUID productId);
}
