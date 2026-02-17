package com.backend.PaymentService.Exception;

import com.backend.PaymentService.Exception.Dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PaymentProcessingException.class,PaymentExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handlePaymentProcessingAndPaymentExistsException(Exception e, HttpServletRequest request)
    {
        return ErrorResponseDto.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handlePaymentNotFoundException(PaymentNotFoundException e, HttpServletRequest request)
    {
        return ErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }

}
