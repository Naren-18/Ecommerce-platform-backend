package com.backend.ProductService.Client.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageKeyValidationResponse {

    private boolean valid;
    private String status;
}
