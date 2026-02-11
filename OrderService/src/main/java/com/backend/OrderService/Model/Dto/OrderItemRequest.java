package com.backend.OrderService.Model.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    @NotNull(message = "id cannot be null")
    private UUID productId;
    @Positive(message = "The quantity must be greater than 0")
    private int quantity;

}
