package com.example.ShareIt.exception;

public class UserHaveNoAccessException extends RuntimeException {
    public UserHaveNoAccessException(String message) {
        super(message);
    }
}
