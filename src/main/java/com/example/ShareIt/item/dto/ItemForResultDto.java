package com.example.ShareIt.item.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Validated
public class ItemForResultDto {

    private long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private Boolean available;

    private List<ItemForResultDto.Comment> comments;

    private Booking lastBooking;

    private Booking nextBooking;

    private Long requestId;


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

