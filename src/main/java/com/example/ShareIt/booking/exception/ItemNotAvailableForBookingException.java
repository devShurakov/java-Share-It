package com.example.ShareIt.booking.exception;

public class ItemNotAvailableForBookingException extends RuntimeException  {
    public ItemNotAvailableForBookingException(String message) {
        super(message);
    }
}
