package ru.practicum.shareit.item.exception;

public class IncorrectParamException extends RuntimeException {
    private final String parameter;

    public IncorrectParamException(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
