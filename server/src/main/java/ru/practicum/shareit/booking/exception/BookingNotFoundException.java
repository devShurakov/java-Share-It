package com.example.ShareIt.booking.exception;

public class BookingNotFoundException extends RuntimeException  {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
