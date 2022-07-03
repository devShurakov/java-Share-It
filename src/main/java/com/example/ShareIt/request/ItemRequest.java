package com.example.ShareIt.request;

import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDate;

@Data
public class ItemRequest {
    int id;
    String description;
    User requestor;
    LocalDate created;
}
