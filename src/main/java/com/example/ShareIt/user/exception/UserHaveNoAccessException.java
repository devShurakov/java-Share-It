package com.example.ShareIt.user.exception;

public class UserHaveNoAccessException extends RuntimeException {
    public UserHaveNoAccessException(String message) {
        super(message);
    }
}
