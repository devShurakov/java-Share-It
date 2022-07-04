package com.example.ShareIt.item;

import com.example.ShareIt.item.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@Validated
@RequestMapping(value = "/items")
public class ItemController {
    private final ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") @NotNull int userId,
                          @RequestBody @Valid ItemDto itemDto) {
        return itemService.create(userId, itemDto);
    }
    @PatchMapping(value = "/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id")  @NotNull int userId,
                          @PathVariable int itemId,
                          @RequestBody ItemDto itemDto) {
        return itemService.update(userId, itemId, itemDto);
    }
    @GetMapping(value = "/{itemId}")
    public ItemDto getItem(@PathVariable int itemId) {
        return itemService.getItem(itemId);
    }
    @GetMapping
    public Collection<ItemDto> getAllItems(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.getAllItems(userId);
    }
    @GetMapping(value = "/search")
    public Collection<ItemDto> search(@RequestParam String text) {
        return itemService.search(text);
    }

}
