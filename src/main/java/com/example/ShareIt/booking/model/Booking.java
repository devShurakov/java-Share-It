package com.example.ShareIt.booking.model;

import com.example.ShareIt.item.model.Item;
import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDate;

@Data
public class Booking {
    int id;
    LocalDate start;
    LocalDate end;
    Item item;
    User booker;
    String status;
}
