package com.example.ShareIt.booking.storage;

import com.example.ShareIt.booking.model.Booking;
import com.example.ShareIt.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Collection<Booking> findBookingsByBookerIdOrderByStartDesc(long userId);

    Collection<Booking> findAllByItem_Owner_IdOrderByStartDesc(long userId);

    Collection<Booking> findBookingsByItemIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now);

    Collection<Booking> findBookingsByItemIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime now);

    Collection<Booking> findAllByBooker_IdAndItem_Id(long userId, long itemId);

    Collection<Booking> findBookingsByBookerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now);

    Collection<Booking> findBookingsByBookerIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime now);

    Collection<Booking> findBookingsByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(long userId, LocalDateTime now, LocalDateTime now1);

    Collection<Booking> findBookingsByBookerIdInAndBookingStatusOrderByStartDesc(Collection<Long> ids2, BookingStatus rejected);

}
