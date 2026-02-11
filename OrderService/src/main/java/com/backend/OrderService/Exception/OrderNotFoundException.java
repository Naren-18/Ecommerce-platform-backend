package com.backend.OrderService.Exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message)
    {
        super(message);
    }
}
