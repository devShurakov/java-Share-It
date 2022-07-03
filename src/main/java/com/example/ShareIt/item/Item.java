package com.example.ShareIt.item;

import lombok.Data;
import org.apache.catalina.User;
@Data
public class Item {
    int id;
    String name;
    String description;
    String available;
    User owner;
    String request;

}
