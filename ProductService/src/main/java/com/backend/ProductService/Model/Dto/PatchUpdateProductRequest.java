package com.backend.ProductService.Model.Dto;

import com.backend.ProductService.Model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchUpdateProductRequest {
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private String currency;
    private ProductStatus status;
}
