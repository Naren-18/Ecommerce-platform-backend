package com.backend.ProductService.Exception;

public class ProductImageValidationException extends RuntimeException{
    public ProductImageValidationException(String message)
    {
        super(message);
    }
}
