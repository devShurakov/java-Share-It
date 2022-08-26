package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    Long id;

    @NotNull
    @NotBlank
    String name;

    @NotNull
    String description;

    @NotNull
    Boolean available;

}
