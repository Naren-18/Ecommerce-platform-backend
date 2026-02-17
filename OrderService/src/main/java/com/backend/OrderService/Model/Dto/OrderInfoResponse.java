package com.backend.OrderService.Model.Dto;

import com.backend.OrderService.Model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfoResponse {
    private String orderNumber;
    private BigDecimal totalAmount;
    private String currency;
    private OrderStatus status;
}
