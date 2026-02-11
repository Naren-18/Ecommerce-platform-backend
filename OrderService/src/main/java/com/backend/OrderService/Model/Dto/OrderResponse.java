package com.backend.OrderService.Model.Dto;

import com.backend.OrderService.Model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private UUID orderId;
    private String orderNumber;
    private OrderStatus status;
    private Instant createdAt;
    private List<OrderItemResponse> items;

}
