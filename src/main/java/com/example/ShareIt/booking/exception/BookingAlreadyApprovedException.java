package com.example.ShareIt.booking.exception;

public class BookingAlreadyApprovedException extends RuntimeException {
    public BookingAlreadyApprovedException(String message) {
        super(message);
    }
}
