package com.backend.ProductService.Model.Dto;

import com.backend.ProductService.Model.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @Size(max=2000,message = "Description should not be more than 2000 characters")
    private String description;

    private String imageUrl;

    @NotNull(message = "Price is required")
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotBlank(message = "Currency is required")
    @Size(min = 3,max = 3 ,message = "Currency should be only 3 characters")
    private String currency;

    private ProductStatus status;

}
