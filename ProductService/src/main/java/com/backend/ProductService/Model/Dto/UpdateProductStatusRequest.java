package com.backend.ProductService.Model.Dto;

import com.backend.ProductService.Model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductStatusRequest {
    private ProductStatus status;
}
