package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.exception.BookingAlreadyApprovedException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.exception.ItemFailedForBookingException;
import ru.practicum.shareit.booking.exception.ItemNotAvailableForBookingException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final UserServiceImpl userService;
    private final ItemService itemService;
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(UserServiceImpl userService,
                              ItemService itemService,
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
            throw new BookingNotFoundException("User not owner");
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

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new BookingNotFoundException("Booking does not exist"));

        if (userId != booking.getBooker().getId() && userId != booking.getItem().getOwner().getId()) {
            throw new BookingNotFoundException("Only the owner can change the status");
        }

        return bookingMapper.mapToBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllForUser(long userId, String state, int from, int size) {

        int page1 = from / size;
        Pageable page = PageRequest.of(page1, size, Sort.by("start").descending());

        if (userService.getUser(userId) == null) {
            throw new BookingNotFoundException("Booking not Found");
        }


        switch (StateStatus.valueOf(state)) {

            case PAST:
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                        .findBookingsByBookerIdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now(),page));

            case FUTURE:
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                        .findBookingsByBookerIdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now(), page));

            case CURRENT:
                LocalDateTime now = LocalDateTime.now();
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                        .findBookingsByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(userId, now, now, page));

            case REJECTED:
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                        .findBookingsByBookerIdAndBookingStatusOrderByStartDesc(userId, page, BookingStatus.REJECTED));

            case WAITING:
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                        .findBookingsByBookerIdAndBookingStatusOrderByStartDesc(userId, page,BookingStatus.WAITING));

            default:
                            return bookingMapper.maptoAllBookingToDto(bookingRepository
                    .findAllBookingsByBookerIdOrderByStartDesc(userId, page));

        }
    }

    @Override
    public List<BookingDto> getAllByForOwner(long userId, String state, int from, int size) {
        int page1 = from / size;
        Pageable page = PageRequest.of(page1, size, Sort.by("start").descending());

        if (userService.getUser(userId) == null) {
                throw new BookingNotFoundException("Booking not Found");
        }

        switch (StateStatus.valueOf(state)) {

            case PAST:
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                        .findAllBookingsByItem_Owner_IdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now(), page));

            case FUTURE:
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                                .findAllBookingsByItem_Owner_IdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now(), page));

            case CURRENT:
                LocalDateTime now = LocalDateTime.now();
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                        .findAllBookingsByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(userId, now, now, page));

            case REJECTED:
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                        .findAllBookingsByItem_Owner_IdAndBookingStatusOrderByStartDesc(userId, page, BookingStatus.REJECTED));

            case WAITING:
                return bookingMapper.maptoAllBookingToDto(bookingRepository
                        .findAllBookingsByItem_Owner_IdAndBookingStatusOrderByStartDesc(userId, page, BookingStatus.WAITING));

            default:
                            return bookingMapper.maptoAllBookingToDto(bookingRepository
                    .findAllBookingsByItem_Owner_IdOrderByStartDesc(userId, page));
        }
    }

}
