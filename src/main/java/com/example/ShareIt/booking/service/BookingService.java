package com.example.ShareIt.booking.service;

import com.example.ShareIt.booking.dto.BookingDto;
import com.example.ShareIt.booking.dto.BookingPostDto;
import com.example.ShareIt.booking.model.Booking;

import java.util.Collection;
import java.util.List;


public interface BookingService {

    BookingDto create(long userId, BookingPostDto bookingPostDto);

    BookingDto aproveBooking(long bookingId, long userId, boolean approved);

    BookingDto getById(Long bookingId, long userId);

    List<BookingDto> getAllForUser(long userId, String state);

    Collection<BookingDto> getAllByForOwner(long userId, String state);

}
