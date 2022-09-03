package ru.practicum.shareit.booking.exception;

public class ItemFailedForBookingException extends RuntimeException  {
    public ItemFailedForBookingException(String message) {
        super(message);
    }
}
