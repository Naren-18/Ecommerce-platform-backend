package com.backend.PaymentService.Client;

import com.backend.PaymentService.Client.Dto.OrderInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(value = "ORDERSERVICE",path = "/order")
public interface OrderClient {

    @GetMapping("/{orderId}/order")
    OrderInfoResponse getOrder(@PathVariable UUID orderId);

    @PostMapping("/{orderId}/cancel")
    String cancelOrder(@PathVariable UUID orderId);

    @PostMapping("/{orderId}/confirm")
    String confirmOrder(@PathVariable UUID orderId);
}
