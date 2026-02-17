package com.backend.PaymentService.Client.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoResponse {
    private String orderNumber;
    private BigDecimal totalAmount;
    private String currency;
    private OrderStatus status;
}
