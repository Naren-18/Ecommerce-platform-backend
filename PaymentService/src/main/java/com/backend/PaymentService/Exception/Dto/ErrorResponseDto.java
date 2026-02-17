package com.backend.PaymentService.Exception.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDto {

    private int status;
    private Object error;
    private String path;

    @Builder.Default
    private Instant timestamp = Instant.now();
}
