package com.example.ShareIt.item;

import lombok.Data;
import org.apache.catalina.User;
@Data
public class ItemDto {
    int id;
    String name;
    String description;
    boolean available;
    User owner;
    String request;
}
