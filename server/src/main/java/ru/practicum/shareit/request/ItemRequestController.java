package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ItemRequestDto create(@RequestHeader(name = header) long userId,
                                 @RequestBody ItemRequestDto itemRequestDto) {

        return itemRequestService.create(userId, itemRequestDto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getRequest(@RequestHeader(name = header) long userId) {

        return itemRequestService.getRequest(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto>  getAllRequest(@RequestHeader("X-Sharer-User-Id")
                                        long userId,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {

        return itemRequestService.getAllRequest(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader("X-Sharer-User-Id")  long userId,
                                             @PathVariable long requestId) {

        return itemRequestService.getItemRequestById(userId, requestId);
    }



}
