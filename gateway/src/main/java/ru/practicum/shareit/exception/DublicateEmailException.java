package ru.practicum.shareit.exception;

public class DublicateEmailException extends RuntimeException {
    public DublicateEmailException(String message) {
        super(message);
    }
}
