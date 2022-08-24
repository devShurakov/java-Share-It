package com.example.ShareIt.booking.exception;

public class UserNotOwnerException extends RuntimeException  {
    public UserNotOwnerException(String message) {
        super(message);
    }
}
