package com.backend.InventoryService.Exception;

import com.backend.InventoryService.Exception.Dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
    public ErrorResponseDto handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request)
    {
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errors)
                .path(request.getRequestURI())
                .build();

    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException e,HttpServletRequest request)
    {
        return ErrorResponseDto.builder()
                .status(HttpStatus.CONFLICT.value())
                .error("Inventory updated by another request. Please retry")
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleProductNotFoundException(ProductNotFoundException e,HttpServletRequest request)
    {
        return ErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(e.getMessage())
                .path(request.getRequestURI())
                .build();

    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleInsufficientStockException(InsufficientStockException e,HttpServletRequest request)
    {
        return ErrorResponseDto.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(ProductExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleProductExistsException(ProductExistsException e,HttpServletRequest request)
    {
        return ErrorResponseDto.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }
}
