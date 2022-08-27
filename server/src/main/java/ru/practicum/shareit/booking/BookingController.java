package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/bookings")
public class BookingController {

    private static final String header = "X-Sharer-User-Id";

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto create(@RequestHeader(name = header) long userId,
                             @RequestBody BookingPostDto bookingPostDto) {
        return bookingService.create(userId, bookingPostDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto aproveBooking(@PathVariable("bookingId") long bookingId,
                                    @RequestHeader(name = header) long userId,
                                    @RequestParam boolean approved) {
        return bookingService.aproveBooking(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@PathVariable("bookingId") Long bookingId, @RequestHeader(name = header) long userId) {
        return bookingService.getById(bookingId, userId);
    }

    @GetMapping()
    public Collection<BookingDto> getAllForUser(@RequestParam(defaultValue = "ALL") String state,
                                                @RequestHeader(name = header) long userId,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return bookingService.getAllForUser(userId, state, from, size);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllByForOwner(@RequestParam(defaultValue = "ALL") String state,
                                                   @RequestHeader(name = header) long userId,
                                                   @RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "10") int size) {
        return bookingService.getAllByForOwner(userId, state, from, size);
    }



}