package com.backend.OrderService.Exception;

public class OrderStateException extends RuntimeException{
    public OrderStateException(String message)
    {
        super(message);
    }
}
