package ru.practicum.shareit.item.exception;

public class UserNotBookedItemException extends RuntimeException {
    public UserNotBookedItemException(String message) {
        super(message);
    }
}
