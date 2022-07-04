package com.example.ShareIt.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Validated
public class ItemDto {
    int id;
    String name;
    @NotNull
    String description;
    @Value("${some.key:null}")
    boolean available;
    public ItemDto(int id, String name, String description, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }

}
