package com.example.ShareIt.booking.service;

import com.example.ShareIt.booking.dto.BookingDto;
import com.example.ShareIt.booking.dto.BookingPostDto;
import com.example.ShareIt.booking.model.Booking;
import com.example.ShareIt.booking.model.StateStatus;

import java.util.Collection;


public interface BookingService {

    BookingDto create(long userId, BookingPostDto bookingPostDto);

    BookingDto aproveBooking(long bookingId, long userId, boolean approved);

    BookingDto getById(Long bookingId, long userId);

    Collection<BookingDto> getAllForUser(long userId, StateStatus state);

    Collection<Booking> sortByState(Collection<Booking> bookings, StateStatus state);

    Collection<BookingDto> getAllByForOwner(long userId, StateStatus state);

}
