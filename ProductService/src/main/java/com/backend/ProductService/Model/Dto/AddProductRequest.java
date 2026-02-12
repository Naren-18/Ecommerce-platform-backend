package com.backend.ProductService.Model.Dto;

import com.backend.ProductService.Model.ProductStatus;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductRequest {

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

//    If U use the below validation then the ternary operator u used in the ProductService will not work
//    @NotNull(message = "status cannot be empty")
    private ProductStatus status;


}
