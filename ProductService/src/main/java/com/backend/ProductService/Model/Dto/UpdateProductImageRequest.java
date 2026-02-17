package com.backend.ProductService.Model.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductImageRequest {

    @NotBlank(message = "The image Key is required")
    @Pattern(regexp = "^media/.*",message = "image Key should start with media/")
    private String imageKey;
}
