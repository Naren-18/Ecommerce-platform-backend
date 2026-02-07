package com.backend.InventoryService.Model.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitInventoryRequest {

    @Min(value = 0,message = "The Initial Quantity should be greater than or equal to 0")
    private int initialQuantity;
}
