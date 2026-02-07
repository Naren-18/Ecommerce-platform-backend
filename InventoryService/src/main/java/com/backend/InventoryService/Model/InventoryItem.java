package com.backend.InventoryService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryItem {

    @Id
    private UUID productId;

    @Column(nullable = false)
    private int availableQuantity = 0;

    @Column(nullable = false)
    private int reservedQuantity = 0;

    @Column(nullable = false)
    private Instant updatedAt;

    @Version
    private long version;

    @PrePersist
    public void onCreate() {
        this.updatedAt=Instant.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt=Instant.now();
    }
}
