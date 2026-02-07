package com.backend.InventoryService.Exception;

public class ProductExistsException extends RuntimeException{
    public ProductExistsException(String message)
    {
        super(message);
    }
}
