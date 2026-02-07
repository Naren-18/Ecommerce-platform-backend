package com.backend.InventoryService.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {

    private UUID productId;
    private int availableQuantity;
    private int reservedQuantity;
    private Instant updatedAt;

}
