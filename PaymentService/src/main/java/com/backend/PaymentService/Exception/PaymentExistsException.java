package com.backend.PaymentService.Exception;

public class PaymentExistsException extends RuntimeException{
    public PaymentExistsException(String message)
    {
        super(message);
    }
}
