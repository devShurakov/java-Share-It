package com.example.ShareIt.booking.mapper;

import com.example.ShareIt.booking.dto.BookingDto;
import com.example.ShareIt.booking.dto.BookingPostDto;
import com.example.ShareIt.booking.model.Booking;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class BookingMapper {

    public static Booking mapToBooking(BookingPostDto bookingPostDto) {

        Booking booking = new Booking();
        booking.setId(bookingPostDto.getId());
        booking.setStart(bookingPostDto.getStart());
        booking.setEnd(bookingPostDto.getEnd());

        return booking;
    }

    public static BookingDto mapToBookingDto(Booking booking) {

        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                new BookingDto.User(booking.getBooker().getId(),booking.getBooker().getName()),
                new BookingDto.Item(booking.getItem().getId(),booking.getItem().getName(),booking.getItem().getDescription()),
                booking.getBookingStatus()

        );
    }

    public static List<BookingDto> maptoAllBookingToDto(Collection<Booking> booking) {

        List<BookingDto> dtos = new ArrayList<>();

        for (Booking x : booking) {
            dtos.add(mapToBookingDto(x));
        }
        return dtos;
    }

}
