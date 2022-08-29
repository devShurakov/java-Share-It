package ru.practicum.shareit.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.exception.BookingAlreadyApprovedException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.exception.ItemFailedForBookingException;
import ru.practicum.shareit.booking.exception.ItemNotAvailableForBookingException;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.exception.*;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.exception.*;
import ru.practicum.shareit.user.exception.IncorrectParamException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice(assignableTypes = {UserController.class, ItemController.class, BookingController.class, ItemRequestController.class,
        UserServiceImpl.class})
public class ErrorHandler {

    @ExceptionHandler(IncorrectParamException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> duplicateEmailException(final IncorrectParamException e) {
        return Map.of("error", String.format("Email already exists"));
    }

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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String>  statusCodeIs404(final UserNotFoundException e) throws IOException {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(UserHaveNoAccessException.class)
    public void statusCodeIs40forItem(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(EmailIsDublicated.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> statusCodeIs409(final EmailIsDublicated e) throws RuntimeException {
        return Map.of("error", e.getMessage());
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

    @ExceptionHandler(NoSuchElementException.class)
    public void statusCodeIs404NoSuchElementException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> stateIsNotValid(final IllegalStateException e) {
        return Map.of("error", String.format(e.getMessage()));
    }

    @ExceptionHandler(DublicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String>  dublicateEmailException(final DublicateEmailException e) throws IOException {
        return Map.of("error", e.getMessage());
    }

}


