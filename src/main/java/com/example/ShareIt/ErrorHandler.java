package com.example.ShareIt;

import com.example.ShareIt.exception.EmailIsDublicated;
import com.example.ShareIt.exception.InvalidUserIdException;
import com.example.ShareIt.user.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Map;

@RestControllerAdvice(assignableTypes = {UserController.class})
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> DataIsNotValid(final MethodArgumentNotValidException e) {
        return Map.of("error", "invalid user data");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> DataIsNotValid(final ConstraintViolationException e) {
        return Map.of("error", "invalid user data");
    }

//    @ExceptionHandler(InvalidUserIdException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Map<String, String> userIdIsNotValid(final InvalidUserIdException e) {
//        return Map.of("error", e.getMessage());
//    }

    @ExceptionHandler(InvalidUserIdException.class)
    public void statusCodeIs400(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(EmailIsDublicated.class)
    public void statusCodeIs409(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }


}


