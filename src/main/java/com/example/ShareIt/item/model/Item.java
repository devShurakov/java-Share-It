package com.example.ShareIt.item.model;

import com.example.ShareIt.request.model.ItemRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Validated
public class Item {
    int id;
    String name;
    @NotNull
    String description;
    boolean available;
    int owner;
    ItemRequest request;

    public Item(int id, String name, String description, boolean available, int owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
    }

}
