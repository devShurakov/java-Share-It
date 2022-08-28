package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping(value = "/bookings")
@Validated
public class BookingController {

    private static final String header = "X-Sharer-User-Id";

    private final BookingClient bookingClient;

    @Autowired
    public BookingController(BookingClient bookingClient) {
        this.bookingClient = bookingClient;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(name = header) long userId,
                                         @RequestBody BookingPostDto bookingPostDto) {

        if (bookingPostDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Booking time in the past.");
        }
        if (bookingPostDto.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("The end time of the booking is in the past.");
        }
        if (bookingPostDto.getStart().isAfter(bookingPostDto.getEnd())) {
            throw new ValidationException("The start time of the booking is later than the end time of the booking.");
        }

        return bookingClient.create(userId, bookingPostDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> aproveBooking(@PathVariable("bookingId") long bookingId,
                                                @RequestHeader(name = header) long userId,
                                                @RequestParam boolean approved) {
        return bookingClient.aproveBooking(bookingId, userId, approved);
    }


    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@PathVariable("bookingId") long bookingId,
                                             @RequestHeader(name = header) long userId) {
        return bookingClient.getById(bookingId, userId);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllForUser(@RequestParam(defaultValue = "ALL") String state,
                                                @RequestHeader(name = header) long userId,
                                                @Min(0) @RequestParam(defaultValue = "0") int from,
                                                @Min(1) @RequestParam(defaultValue = "10") int size) {
        checkPageBoarder(from, size);
        String status = checkBookingStatus(state);
        return bookingClient.getAllForUser(userId, status, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllByForOwner(@RequestParam(defaultValue = "ALL") String state,
                                                   @RequestHeader(name = header) long userId,
                                                   @Min(0) @RequestParam(defaultValue = "0") int from,
                                                   @Min(1) @RequestParam(defaultValue = "10") int size) {
        checkPageBoarder(from, size);
        String status = checkBookingStatus(state);
        return bookingClient.getAllByForOwner(userId, status, from, size);
    }

    private String checkBookingStatus(String status) {
        String status1 = status.toUpperCase();
        if (!List.of("ALL", "CURRENT", "PAST", "FUTURE", "WAITING", "REJECTED", "APPROVED").contains(status)) {
            throw new IllegalArgumentException(String.format("Unknown state: %s", status));
        }
        return status1;
    }

    private void checkPageBoarder(int from, int size) {
        if (from < 0) {
            throw new ValidationException(String.format("неверное значение from %d.", from));
        }
        if (size < 1) {
            throw new ValidationException(String.format("неверное значение size %d.", size));
        }
    }


}