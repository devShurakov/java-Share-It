package com.example.ShareIt.booking.storage;

import com.example.ShareIt.booking.model.Booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Collection<Booking> findBookingsByBookerIdOrderByStartDesc(long userId);
    Collection<Booking> findAllByItem_Owner_IdOrderByStartDesc(long userId);
    Collection<Booking> findBookingsByItemIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now);
    Collection<Booking> findBookingsByItemIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime now);
    Collection<Booking> findAllByBooker_IdAndItem_Id(long userId, long itemId);
}
