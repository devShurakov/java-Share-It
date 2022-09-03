package ru.practicum.shareit.user.exception;

public class EmailIsDublicated extends RuntimeException {
    public EmailIsDublicated(String message) {
        super(message);
    }
}
