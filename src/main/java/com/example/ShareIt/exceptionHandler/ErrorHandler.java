package com.example.ShareIt.exceptionHandler;

import com.example.ShareIt.booking.BookingController;
import com.example.ShareIt.booking.exception.BookingAlreadyApprovedException;
import com.example.ShareIt.booking.exception.BookingNotFoundException;
import com.example.ShareIt.booking.exception.ItemFailedForBookingException;
import com.example.ShareIt.booking.exception.ItemNotAvailableForBookingException;
import com.example.ShareIt.item.ItemController;
import com.example.ShareIt.item.exception.*;
import com.example.ShareIt.user.exception.EmailIsDublicated;
import com.example.ShareIt.user.exception.InvalidUserIdException;
import com.example.ShareIt.user.exception.UserHaveNoAccessException;
import com.example.ShareIt.user.exception.UserNotFoundException;
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

@RestControllerAdvice(assignableTypes = {UserController.class, ItemController.class, BookingController.class})
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> dataIsNotValid(final MethodArgumentNotValidException e) {
        return Map.of("error", "invalid user data");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> dataIsNotValid(final ConstraintViolationException e) {
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

    @ExceptionHandler(ItemDoesNotBelongException.class)
    public void statusCodeIs404forItems(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public void statusCodeIs404forItemsWhenNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

        @ExceptionHandler(ItemNotAvailableForBookingException.class)
    public void statusCodeIs400forBooking(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ItemFailedForBookingException.class)
    public void statusCodeIs404forBooking(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
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

    @ExceptionHandler(BookingNotFoundException.class)
    public void statusCodeIs404ForBookingGetUnknown(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(BookingAlreadyApprovedException.class)
    public void statusCodeIs400ForBookingAlreadyException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(UserNotBookedItemException.class)
    public void statusCodeIs400CommentException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(CommentFailedException.class)
    public void statusCodeIs400CommentFailedException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

}


