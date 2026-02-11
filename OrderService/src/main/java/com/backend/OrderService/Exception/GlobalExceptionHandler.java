package com.backend.OrderService.Exception;

import com.backend.OrderService.Exception.Dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleValidationsExceptions(MethodArgumentNotValidException e,HttpServletRequest request)
    {
        Map<String,String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));

        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errors)
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler({OrderFailedException.class,OrderStateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleOrderExceptions(Exception e, HttpServletRequest request)
    {
        return ErrorResponseDto.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleOrderNotFoundException(OrderNotFoundException e,HttpServletRequest request)
    {
        return ErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }

}
