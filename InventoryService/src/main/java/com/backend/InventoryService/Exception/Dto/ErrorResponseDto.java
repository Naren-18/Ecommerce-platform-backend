package com.backend.InventoryService.Exception.Dto;

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

    @Builder.Default //This annotations takes care of initializing timestamp value while using builder to build otherwise it may initialize null value if not used
    private Instant timestamp = Instant.now();
}
