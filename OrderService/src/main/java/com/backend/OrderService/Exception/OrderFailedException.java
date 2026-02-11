package com.backend.OrderService.Exception;

public class OrderFailedException extends RuntimeException{
    public OrderFailedException(String message)
    {
        super(message);
    }
}
