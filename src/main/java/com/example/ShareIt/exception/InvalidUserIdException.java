package com.example.ShareIt.exception;

public class InvalidUserIdException  extends RuntimeException {
    public InvalidUserIdException(String message) {
        super(message);
    }
}