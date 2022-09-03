package ru.practicum.shareit.booking;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    Long id;

    @NotNull
    @DateTimeFormat(fallbackPatterns = "yyy-MM-ddTHH:mm:ss")
    private LocalDateTime start;

    @NotNull
    @DateTimeFormat(fallbackPatterns = "yyy-MM-ddTHH:mm:ss")
    private LocalDateTime end;

    private Item item;

    private User booker;

    private String status;

    public BookingDto(long id, LocalDateTime start, LocalDateTime end, BookingDto.User booker, BookingDto.Item item, BookingStatus status) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.booker = booker;
        this.item = item;
        this.status = status.name();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {

        private Long id;

        private String name;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {

        private Long id;

        private String name;

        private String description;

    }

}
