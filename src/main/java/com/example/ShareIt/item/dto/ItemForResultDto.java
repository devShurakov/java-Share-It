package com.example.ShareIt.item.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemForResultDto {

    private long id;

    private String name;

    private String description;

    private boolean available;

    private List<ItemForResultDto.Comment> comments;

    private Booking lastBooking;

    private Booking nextBooking;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Booking {

        private long id;

        private long bookerId;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Comment {

        Long id;

        String text;

        String authorName;

        LocalDateTime created;
    }

}

