package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Collection<Booking> findBookingsByItemIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now);

    Collection<Booking> findBookingsByItemIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime now);

    Collection<Booking> findAllByBooker_IdAndItem_Id(long userId, long itemId);

    List<Booking> findBookingsByBookerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now, Pageable page);

    List<Booking> findBookingsByBookerIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime now, Pageable page);

    List<Booking> findBookingsByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(long userId, LocalDateTime now,
                                                                                  LocalDateTime now1, Pageable page);

    List<Booking> findBookingsByBookerIdAndBookingStatusOrderByStartDesc(long userId, Pageable page,
                                                                         BookingStatus rejected);

    List<Booking> findAllBookingsByBookerIdOrderByStartDesc(long userId, Pageable page);

    List<Booking> findAllBookingsByItem_Owner_IdOrderByStartDesc(long userId, Pageable page);


    List<Booking> findAllBookingsByItem_Owner_IdAndStartAfterOrderByStartDesc(long userId, LocalDateTime now,
                                                                              Pageable page);

    List<Booking> findAllBookingsByItem_Owner_IdAndBookingStatusOrderByStartDesc(long userId, Pageable page,
                                                                                 BookingStatus waiting);

    List<Booking> findAllBookingsByItem_Owner_IdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now,
                                                                             Pageable page);

    List<Booking> findAllBookingsByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(long userId,
                                                                                          LocalDateTime now,
                                                                                          LocalDateTime now1,
                                                                                          Pageable page);
}
