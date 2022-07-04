package com.example.ShareIt.booking.dto;

import org.apache.catalina.User;

import java.time.LocalDate;

public class BookingDto {
    int id;
    LocalDate start;
    LocalDate end;
    static Item item;
    User booker;
    String status;

    static class Item{
        int id;
        String name;
    }

}
