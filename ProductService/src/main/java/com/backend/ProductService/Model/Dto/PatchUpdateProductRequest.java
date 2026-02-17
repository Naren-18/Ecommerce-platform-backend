package com.backend.ProductService.Model.Dto;

import com.backend.ProductService.Model.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Pattern(regexp = "^media/.*",message = "image Key should start with media/")
    private String imageKey;

    @DecimalMin("0.0")
    private BigDecimal price;

    @Size(min = 3,max = 3 ,message = "Currency should be only 3 characters")
    private String currency;
    private ProductStatus status;
}
