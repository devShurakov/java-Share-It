package com.example.ShareIt.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
