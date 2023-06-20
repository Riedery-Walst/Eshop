package ru.kobaclothes.eshop.exception;

public class InvalidAccountInfoException extends RuntimeException {
    public InvalidAccountInfoException(String message) {
        super(message);
    }
}
