package com.example.ShareIt.exception.handler;

import com.example.ShareIt.exception.*;
import com.example.ShareIt.item.ItemController;
import com.example.ShareIt.user.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Map;

@RestControllerAdvice(assignableTypes = {UserController.class, ItemController.class})
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> DataIsNotValid(final MethodArgumentNotValidException e) {
        return Map.of("error", "invalid user data");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> DataIsNotValid(final ConstraintViolationException e) {
        return Map.of("error", "invalid item data");
    }

    @ExceptionHandler(InvalidUserIdException.class)
    public void statusCodeIs400(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(InvalidItemException.class)
    public void statusCodeIs400forItems(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public void statusCodeIs404(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
    @ExceptionHandler(UserHaveNoAccessException.class)
    public void statusCodeIs40forItem(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(EmailIsDublicated.class)
    public void statusCodeIs409(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public void statusCodeIs500(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}


