package com.example.ShareIt.booking;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Status {
    WAITING("Новое бронирование, ожидает одобрения"),
    APPROVED("Бронирование подтверждено владельцем"),
    REJECTED("Бронирование отклонено владельцем"),
    CANCELED("Бронирование отменено создателем");
    private final String title;
    Status(String title) {
        this.title = title;
    }
    public String getStatus() {
        return title;
    }
}
