package com.backend.PaymentService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;

    @Column(nullable = false,unique = true)
    private UUID orderId;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false,length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    //provider (String: “MOCK”) (optional) // May implement in the next version

    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    private void onCreate()
    {
        this.status = PaymentStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    private void onUpdate()
    {
        this.updatedAt = Instant.now();
    }
}
