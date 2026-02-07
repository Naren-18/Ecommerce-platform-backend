package com.backend.InventoryService.Model.Dto;


import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityRequest {

    @Positive(message = "The Quantity should be greater than 0")
    private int quantity;
}
