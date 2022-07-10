package com.example.ShareIt.request.dto;

import org.apache.catalina.User;

import java.time.LocalDate;

public class ItemRequestDto {
    int id;
    String description;
    int requestor;
    LocalDate created;
}
