package ru.kobaclothes.eshop.exception;

public class ProductCategoryAlreadyExistException extends RuntimeException {
    public ProductCategoryAlreadyExistException(String message) {
        super(message);
    }
}
