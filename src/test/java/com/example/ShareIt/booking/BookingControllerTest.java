package com.example.ShareIt.booking;


import com.example.ShareIt.booking.dto.BookingDto;
import com.example.ShareIt.booking.dto.BookingPostDto;
import com.example.ShareIt.booking.model.BookingStatus;
import com.example.ShareIt.booking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {
    @Mock
    private BookingServiceImpl service;

    @InjectMocks
    private BookingController controller;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    private BookingDto.User user = new BookingDto.User(
            1l,
            "Denis");
    private BookingDto.Item item = new BookingDto.Item(
            1L,
            "Отвертка",
            "Крестовая");

    private BookingPostDto postBookingDto = new BookingPostDto(
            1L,
            LocalDateTime.parse(LocalDateTime.of(2022, 3, 1, 10, 11)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))),
            LocalDateTime.parse(LocalDateTime.of(2022, 3, 1, 19, 11)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))),
            item.getId()
    );

    private BookingDto bookingDto = new BookingDto(
            1L,
            LocalDateTime.parse(LocalDateTime.of(2022, 3, 1, 10, 11)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))),
            LocalDateTime.parse(LocalDateTime.of(2022, 3, 1, 19, 11)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))),
            user,
            item,
            BookingStatus.REJECTED
    );

    @Test
    void createBookingTest() throws Exception {
        String bookingJson = "{\"itemId\": 2, \"start\": \"2022-03-01T10:11:00\",\"end\": \"2022-03-01T19:11:00\"}";
        when(service.create(anyLong(), any()))
                .thenReturn(bookingDto);

        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1L)
                        .content(bookingJson)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void aproveBookingTest() throws Exception {
        mvc.perform(patch("/bookings/{bookingId}", "1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("approved", "true"))
                .andExpect(status().isOk());
        verify(service, Mockito.times(1)).aproveBooking(1L, 1,true);
    }

    @Test
    void getBookingTest() throws Exception {
        when(service.getById(anyLong(),anyLong()))
                .thenReturn(bookingDto);

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class));
    }

    @Test
    void getAllBookingByUserIdTest() throws Exception {
        when(service.getAllForUser(anyLong(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("size", "1")
                        .param("from", "1")
                        .param("state", "ALL"))
                .andExpect(status().isOk());


        verify(service, times(1)).getAllForUser(1L, "ALL", 1, 1);
    }

    @Test
    void getAllByForOwnerTest() throws Exception {
        when(service.getAllByForOwner(anyLong(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("size", "1")
                        .param("from", "1")
                        .param("state", "ALL"))
                .andExpect(status().isOk());


        verify(service, times(1)).getAllByForOwner(1L, "ALL", 1, 1);
    }
}
