package com.example.ShareIt.booking.service.impl;

import com.example.ShareIt.booking.mapper.BookingMapper;
import com.example.ShareIt.booking.dto.BookingPostDto;
import com.example.ShareIt.booking.exception.*;
import com.example.ShareIt.booking.model.BookingStatus;
import com.example.ShareIt.booking.dto.BookingDto;
import com.example.ShareIt.booking.model.Booking;
import com.example.ShareIt.booking.model.StateStatus;
import com.example.ShareIt.booking.service.BookingService;
import com.example.ShareIt.booking.storage.BookingRepository;
import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.item.service.impl.ItemServiceImpl;
import com.example.ShareIt.user.model.User;
import com.example.ShareIt.user.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;


@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final UserService userService;
    private final ItemServiceImpl itemService;
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(UserService userService,
                              ItemServiceImpl itemService,
                              BookingMapper bookingMapper,
                              BookingRepository bookingRepository) {
        this.userService = userService;
        this.itemService = itemService;
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingDto create(long userId, BookingPostDto bookingPostDto) {
        Booking bookingPost = bookingMapper.mapToBooking(bookingPostDto);
        User user = userService.getUser((int) userId);
        Item item = itemService.getItem(bookingPostDto.getItemId());

        if (item.getOwner().getId() == userId) {
            throw new ItemFailedForBookingException("the owner cannot book the item");
        }
        if (item.getAvailable() == false) {
            throw new ItemNotAvailableForBookingException("Item is not available");
        }
        if (bookingPost.getEnd().isBefore(LocalDateTime.now())) {
            throw new ItemNotAvailableForBookingException("Booking end date in the past");
        }

        if (bookingPost.getStart().isBefore(LocalDateTime.now())) {
            throw new ItemNotAvailableForBookingException("Booking date in the past");
        }
        if (bookingPost.getEnd().isBefore(bookingPost.getStart())) {
            throw new ItemNotAvailableForBookingException("Booking date in the past");
        }
        bookingPost.setBooker(user);
        bookingPost.setItem(item);
        bookingPost.setBookingStatus(BookingStatus.WAITING);
        return bookingMapper.mapToBookingDto(bookingRepository.save(bookingPost));
    }

    @Override
    public BookingDto aproveBooking(long bookingId, long userId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Item item = itemService.getItem(booking.getItem().getId());
        if (userService.getUser(userId) == null) {
            throw new BookingNotFoundException("User not found");
        }
        if (item.getOwner().getId() != userId) {
            throw new BookingNotFoundException("user not owner");
        }
        if (booking.getBookingStatus().equals(BookingStatus.APPROVED)) {
            throw new BookingAlreadyApprovedException("Already approved");
        }

        booking.setBookingStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return bookingMapper.mapToBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getById(Long bookingId, long userId) {
        if (userService.getUser((int) userId) == null) {
            throw new BookingNotFoundException("User does not exist");
        }

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> {
            return new BookingNotFoundException("Booking does not exist");
        });
        if (userId != booking.getBooker().getId() && userId != booking.getItem().getOwner().getId()) {
            throw new BookingNotFoundException("Only the owner can change the status");
        }
        return bookingMapper.mapToBookingDto(booking);
    }

    @Override
    public Collection<BookingDto> getAllForUser(long userId, StateStatus state) {
        Collection<Booking> bookings = bookingRepository.findBookingsByBookerIdOrderByStartDesc(userId);
        return bookingMapper.maptoAllBookingToDto(sortByState(bookings, state));
    }

    @Override
    public Collection<Booking> sortByState(Collection<Booking> bookings, StateStatus state) {

        switch (state) {
            case ALL:
                return new ArrayList<>(bookings);
            case PAST:
                return bookings.stream()
                        .filter((booking) -> booking.getEnd().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case FUTURE:
                return bookings.stream()
                        .filter((booking) -> booking.getStart().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case CURRENT:
                return bookings.stream()
                        .filter((booking) -> LocalDateTime.now().isAfter(booking.getStart())
                                && LocalDateTime.now().isBefore(booking.getEnd()))
                        .collect(Collectors.toList());
            case REJECTED:
                return bookings.stream()
                        .filter(booking -> booking.getBookingStatus().equals(BookingStatus.REJECTED))
                        .collect(Collectors.toList());
            case WAITING:
                return bookings.stream()
                        .filter(booking -> booking.getBookingStatus().equals(BookingStatus.WAITING))
                        .collect(Collectors.toList());
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public Collection<BookingDto> getAllByForOwner(long userId, StateStatus state) {
        Collection<Booking> bookings = bookingRepository.findAllByItem_Owner_IdOrderByStartDesc(userId);
        return bookingMapper.maptoAllBookingToDto(sortByState(bookings, state));
    }

}

