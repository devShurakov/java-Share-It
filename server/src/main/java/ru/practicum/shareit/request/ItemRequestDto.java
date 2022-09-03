package ru.practicum.shareit.request;

import ru.practicum.shareit.item.ItemForResultDto;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Validated
@Data
public class ItemRequestDto {

    Long id;

    @NotNull
    private String description;

    private User request;

    private LocalDateTime created = LocalDateTime.now();

    private List<ItemForResultDto> items;



    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {

        Long id;

        private String name;

    }

}
