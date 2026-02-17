package com.backend.PaymentService.Client.Dto;

public enum OrderStatus {
    CREATED, //→ order created (before inventory reserve)

    RESERVED, //→ inventory reserved successfully

    CONFIRMED, //→ payment done + inventory committed

    CANCELLED, //→ user cancelled → inventory released

    FAILED, //→ failure happened → inventory released (if reserved)
}
