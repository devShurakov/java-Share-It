package com.example.ShareIt.user.exception;

public class EmailIsDublicated extends RuntimeException {
    public EmailIsDublicated(String message) {
        super(message);
    }
}
