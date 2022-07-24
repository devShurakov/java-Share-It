package com.example.ShareIt.request.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemRequestDto {

    Long id;
    @NotNull
    private String description;
    @NotNull
    private User request;
    @NotNull
    private LocalDateTime created;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        Long id;
        private String name;
    }

}
