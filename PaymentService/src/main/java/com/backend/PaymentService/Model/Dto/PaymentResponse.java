package com.backend.PaymentService.Model.Dto;

import com.backend.PaymentService.Model.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // This hides fields that are null
public class PaymentResponse {
    private UUID paymentId;
    private UUID orderId;
    private String orderNumber;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
