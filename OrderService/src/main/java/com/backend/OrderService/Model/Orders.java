package com.backend.OrderService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @Column(nullable = false, unique = true)
    private String orderNumber; //Need to generate the Number automatically but the id must start with ORD and should have only 5 characters after that

    @Column(nullable = false)
    private String userId; //username is the email of the user used to login

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalAmount;
    private String currency;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems;

    @PrePersist
    public void onCreate()
    {
        this.orderNumber = generateOrderNumber();
        this.status = OrderStatus.CREATED;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    private String generateOrderNumber() {
        return "ORD"+UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

    @PreUpdate
    public void onUpdate()
    {
        this.updatedAt = Instant.now();
    }


}
