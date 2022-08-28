package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@Validated
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private static final String header = "X-Sharer-User-Id";

    private final ItemRequestClient itemRequestClient;

    @Autowired
    public ItemRequestController(ItemRequestClient itemRequestClient) {

        this.itemRequestClient = itemRequestClient;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(name = header) @NotNull long userId,
                                         @RequestBody @Valid ItemRequestDto itemRequestDto) {

        return itemRequestClient.create(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getRequest(@RequestHeader(name = header) @NotNull int userId) {

        return itemRequestClient.getRequest(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object>  getAllRequest(@RequestHeader("X-Sharer-User-Id")
                                        @NotNull int userId,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {

        return itemRequestClient.getAllRequest(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(@RequestHeader("X-Sharer-User-Id") @NotNull int userId,
                                             @PathVariable int requestId) {

        return itemRequestClient.getItemRequestById(userId, requestId);
    }



}
