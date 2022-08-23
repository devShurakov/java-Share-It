//package com.example.ShareIt.booking;
//
//import com.example.ShareIt.booking.model.Booking;
//import com.example.ShareIt.booking.model.BookingStatus;
//import com.example.ShareIt.booking.storage.BookingRepository;
//import com.example.ShareIt.item.model.Item;
//import com.example.ShareIt.user.model.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import static org.assertj.core.api.BDDAssertions.then;
//
//@DataJpaTest
//@AutoConfigureTestDatabase
//public class BookingRepositoryTest {
//
//    @Autowired
//    private TestEntityManager em;
//
//    @Autowired
//    private BookingRepository repository;
//
//    User owner;
//    User booker;
//    Item item;
//
//    @BeforeEach
//    void setUp() {
//        owner = User.builder().name("newName").email("newUser@user.com").build();
//        booker = User.builder().name("requestor").email("requestor@user.com").build();
//        item = Item.builder()
//                .name("Дрель")
//                .description("description")
//                .available(true)
//                .owner(owner)
//                .build();
//        em.persist(owner);
//        em.persist(booker);
//        em.persist(item);
//    }
//
//    @Test
//    void findByBookerIdOrderByStartDesc() {
//        Booking booking = Booking.builder()
//                .booker(booker)
//                .item(item)
//                .start(LocalDateTime.now().plusMinutes(10))
//                .end(LocalDateTime.now().plusDays(1))
//                .bookingStatus(BookingStatus.WAITING).build();
//
//        em.persist(booking);
//        Pageable page = PageRequest.of(0, 10);
//        List<Booking> result = repository.findAllBookingsByBookerIdOrderByStartDesc(booker.getId(), page);
//        then(result).size().isEqualTo(1);
//        then(result).containsExactlyElementsOf(List.of(booking));
//
//        User booker = new User(null, "User name 1", "email@example.com");
//        User owner = new User(null, "User name 2", "email2@example.com");
//        Item item = new Item(null, "Item name", "Item description", true, owner, null);
//        final LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 1, 9, 50);
//        final LocalDateTime end = start.plusDays(1);
//        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.WAITING);
//        em.persist(owner);
//        em.persist(booker);
//        em.persist(item);
//        em.persist(booking);
//
//        // When
//        Collection<Booking> result =
//                repository.findBookingsByBookerIdOrderByStartDesc(booker.getId());
//
//        // Then
//        then(result).size().isEqualTo(1);
//        then(result).containsExactlyElementsOf(List.of(booking));
//
//
//
//    }
////
////    @Test
////    void findByBookerIdAndStartBeforeAndEndAfter() {
////
////        LocalDateTime before = LocalDateTime.now().minusDays(1);
////        LocalDateTime after = LocalDateTime.now().plusDays(1);
////        Booking booking = Booking.builder()
////                .start(before)
////                .end(after)
////                .booker(booker)
////                .item(item)
////                .status(BookingStatus.WAITING).build();
////        em.persist(booking);
////        Collection<Booking> result = repository.findByBookerIdAndStartBeforeAndEndAfter(booker.getId(), LocalDateTime.now(), LocalDateTime.now(), Sort.by(Sort.Direction.DESC,
////                "start"));
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByBookerIdAndEndBeforeOrderByStartDesc() {
////        LocalDateTime before = LocalDateTime.now().minusDays(10);
////        LocalDateTime after = LocalDateTime.now().minusDays(8);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Booking> result = repository.findByBookerIdAndEndBeforeOrderByStartDesc(booker.getId(), LocalDateTime.now());
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByBookerIdAndStartAfterOrderByStartDesc() {
////        LocalDateTime before = LocalDateTime.now().plusDays(10);
////        LocalDateTime after = LocalDateTime.now().plusDays(11);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Booking> result = repository.findByBookerIdAndStartAfterOrderByStartDesc(booker.getId(), LocalDateTime.now());
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByBookerIdAndStatusOrderByStartDesc() {
////        LocalDateTime before = LocalDateTime.now().plusDays(10);
////        LocalDateTime after = LocalDateTime.now().plusDays(11);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Booking> result = repository.findByBookerIdAndStatusOrderByStartDesc(booker.getId(), Status.WAITING);
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByItemIdInOrderByStartDesc() {
////        LocalDateTime before = LocalDateTime.now().plusDays(10);
////        LocalDateTime after = LocalDateTime.now().plusDays(11);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Long> itemsId = new ArrayList<>();
////        itemsId.add(item.getId());
////        Collection<Booking> result = repository.findByItemIdInOrderByStartDesc(itemsId);
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByItemIdInAndStartBeforeAndEndAfter() {
////        LocalDateTime before = LocalDateTime.now().minusDays(1);
////        LocalDateTime after = LocalDateTime.now().plusDays(11);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Long> itemsId = new ArrayList<>();
////        itemsId.add(item.getId());
////        Collection<Booking> result = repository.findByItemIdInAndStartBeforeAndEndAfter(itemsId, LocalDateTime.now(), LocalDateTime.now(), Sort.by(Sort.Direction.DESC,
////                "start"));
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByItemIdInAndEndBeforeOrderByStartDesc() {
////        LocalDateTime before = LocalDateTime.now().minusDays(12);
////        LocalDateTime after = LocalDateTime.now().minusDays(11);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Long> itemsId = new ArrayList<>();
////        itemsId.add(item.getId());
////        Collection<Booking> result = repository.findByItemIdInAndEndBeforeOrderByStartDesc(itemsId, LocalDateTime.now());
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByItemIdInAndStartAfterOrderByStartDesc() {
////        LocalDateTime before = LocalDateTime.now().plusDays(11);
////        LocalDateTime after = LocalDateTime.now().plusDays(12);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Long> itemsId = new ArrayList<>();
////        itemsId.add(item.getId());
////        Collection<Booking> result = repository.findByItemIdInAndStartAfterOrderByStartDesc(itemsId, LocalDateTime.now());
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByItemIdInAndStatusOrderByStartDesc() {
////        LocalDateTime before = LocalDateTime.now().plusDays(11);
////        LocalDateTime after = LocalDateTime.now().plusDays(12);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Long> itemsId = new ArrayList<>();
////        itemsId.add(item.getId());
////        Collection<Booking> result = repository.findByItemIdInAndStatusOrderByStartDesc(itemsId, Status.WAITING);
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByItemIdAndEndBeforeOrderByStartDesc() {
////        LocalDateTime before = LocalDateTime.now().minusDays(12);
////        LocalDateTime after = LocalDateTime.now().minusDays(11);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Long> itemsId = new ArrayList<>();
////        itemsId.add(item.getId());
////        Collection<Booking> result = repository.findByItemIdAndEndBeforeOrderByStartDesc(item.getId(), LocalDateTime.now());
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByItemIdAndStartAfterOrderByStartDesc() {
////        LocalDateTime before = LocalDateTime.now().plusDays(11);
////        LocalDateTime after = LocalDateTime.now().plusDays(12);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Long> itemsId = new ArrayList<>();
////        itemsId.add(item.getId());
////        Collection<Booking> result = repository.findByItemIdAndStartAfterOrderByStartDesc(item.getId(), LocalDateTime.now());
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
////
////    @Test
////    void findByBookerIdAndItemIdAndEndBefore() {
////        LocalDateTime before = LocalDateTime.now().minusDays(12);
////        LocalDateTime after = LocalDateTime.now().minusDays(11);
////        Booking booking = Booking.builder()
////                .booker(booker)
////                .item(item)
////                .start(before)
////                .end(after)
////                .status(Status.WAITING).build();
////        em.persist(booking);
////        Collection<Long> itemsId = new ArrayList<>();
////        itemsId.add(item.getId());
////        Collection<Booking> result = repository.findByBookerIdAndItemIdAndEndBefore(booker.getId(), item.getId(), LocalDateTime.now(), Sort.by(Sort.Direction.DESC,
////                "start"));
////        then(result).size().isEqualTo(1);
////        then(result).containsExactlyElementsOf(List.of(booking));
////    }
//}
