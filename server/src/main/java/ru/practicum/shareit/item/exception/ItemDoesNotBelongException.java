package ru.practicum.shareit.item.exception;

public class ItemDoesNotBelongException extends RuntimeException {
    public ItemDoesNotBelongException(String message) {
        super(message);
    }
}
