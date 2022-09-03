package ru.practicum.shareit.user.exception;

public class IncorrectParamException extends RuntimeException {
    public IncorrectParamException(String message) {
        super(message);
    }
}
