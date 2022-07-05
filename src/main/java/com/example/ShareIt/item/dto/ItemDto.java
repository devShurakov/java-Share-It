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
}
