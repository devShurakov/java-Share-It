package ru.practicum.shareit.booking;

import java.util.Collection;
import java.util.List;


public interface BookingService {

    BookingDto create(long userId, BookingPostDto bookingPostDto);

    BookingDto aproveBooking(long bookingId, long userId, boolean approved);

    BookingDto getById(Long bookingId, long userId);

    List<BookingDto> getAllForUser(long userId, String state, int from, int size);

    Collection<BookingDto> getAllByForOwner(long userId, String state, int from, int size);

}
