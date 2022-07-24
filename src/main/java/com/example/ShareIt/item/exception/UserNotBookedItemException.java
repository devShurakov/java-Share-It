package com.example.ShareIt.item.exception;

public class UserNotBookedItemException extends RuntimeException {
    public UserNotBookedItemException(String message) {
        super(message);
    }
}
