package com.backend.ProductService.Model.Dto;

import com.backend.ProductService.Model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private UUID productId;
    private String name;
    private String description;
    private String imageKey;
    private BigDecimal price;
    private String currency;

    private ProductStatus status;
    private Instant createdAt;

    private Instant updatedAt;
}
