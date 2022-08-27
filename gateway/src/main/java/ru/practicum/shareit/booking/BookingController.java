package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;


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
        return bookingClient.getAllForUser(userId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllByForOwner(@RequestParam(defaultValue = "ALL") String state,
                                                   @RequestHeader(name = header) long userId,
                                                   @Min(0) @RequestParam(defaultValue = "0") int from,
                                                   @Min(1) @RequestParam(defaultValue = "10") int size) {
        return bookingClient.getAllByForOwner(userId, state, from, size);
    }



}