package com.example.ShareIt.item.controller;

import com.example.ShareIt.item.ItemController;
import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.dto.ItemForResultDto;
import com.example.ShareIt.item.service.impl.ItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    @Mock
    private ItemServiceImpl itemService;

    @InjectMocks
    private ItemController controller;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private ItemForResultDto itemOne = new ItemForResultDto(
            1L,
            "Отвертка",
            "Крестовая",
            true,
            null,
            null,
            null,
            null);

    private ItemForResultDto itemTwo = new ItemForResultDto(
            1L,
            "Доска",
            "Для забора",
            true,
            null,
            null,
            null,
            null);

    private ItemDto itemOneUpdated = new ItemDto(
            1L,
            "Отвертка",
            "Крестовая, для закручивания устройств соединений с крестовой шляпкой",
            true);

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void createItemTest() throws Exception {
        when(itemService.create(anyInt(), any()))
                .thenReturn(itemOne);

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1l)
                        .content(mapper.writeValueAsString(itemOne))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemOne.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemOne.getName())))
                .andExpect(jsonPath("$.description", is(itemOne.getDescription())))
                .andExpect(jsonPath("$.available", is(itemOne.getAvailable())));
    }

    @Test
    void updateItemTest() throws Exception {
        when(itemService.update(anyInt(), anyInt(),any()))
                .thenReturn(itemOneUpdated);

        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1l)
                        .content(mapper.writeValueAsString(itemOne))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemOneUpdated.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemOneUpdated.getName())))
                .andExpect(jsonPath("$.description", is(itemOneUpdated.getDescription())))
                .andExpect(jsonPath("$.available", is(itemOneUpdated.getAvailable())));
    }

    @Test
    void testGetAllByUserId() throws Exception {
        when(itemService.getAllItems(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(itemOne, itemTwo));

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1l)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("size", "0")
                        .param("from", "0"))
                .andExpect(status().isOk());
    }


}
