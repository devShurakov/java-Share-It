package com.example.ShareIt.exception;

public class EmailIsDublicated extends RuntimeException {
    public EmailIsDublicated(String message) {
        super(message);
    }
}
