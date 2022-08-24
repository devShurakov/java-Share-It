package com.example.ShareIt.request;

import ru.practicum.shareit.request.ItemRequestDto;
import ru.practicum.shareit.request.ItemRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private static final String header = "X-Sharer-User-Id";

    private final ItemRequestService itemRequestService;

    @Autowired
    public ItemRequestController(ItemRequestService itemRequestService) {

        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ItemRequestDto create(@RequestHeader(name = header) @NotNull int userId,
                                 @RequestBody @Valid ItemRequestDto itemRequestDto) {

        return itemRequestService.create(userId, itemRequestDto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getRequest(@RequestHeader(name = header) @NotNull long userId) {

        return itemRequestService.getRequest(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto>  getAllRequest(@RequestHeader("X-Sharer-User-Id")
                                        @NotNull long userId,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {

        return itemRequestService.getAllRequest(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader("X-Sharer-User-Id") @NotNull long userId,
                                             @PathVariable long requestId) {

        return itemRequestService.getItemRequestById(userId, requestId);
    }



}
