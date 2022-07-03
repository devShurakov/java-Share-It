package com.example.ShareIt.request;

import org.apache.catalina.User;

import java.time.LocalDate;

public class ItemRequestDto {
    int id;
    String description;
    User requestor;
    LocalDate created;
}
