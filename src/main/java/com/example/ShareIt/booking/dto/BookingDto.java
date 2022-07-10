package com.example.ShareIt.booking.dto;

import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDate;

@Data
public class BookingDto {
    int id;
    LocalDate start;
    LocalDate end;
    int item;
    int booker;
    String status;
}


