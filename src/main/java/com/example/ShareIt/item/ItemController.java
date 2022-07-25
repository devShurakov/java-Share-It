package com.example.ShareIt.item;

import com.example.ShareIt.item.dto.CommentDto;
import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.dto.ItemForResultDto;
import com.example.ShareIt.item.service.ItemService;
import com.example.ShareIt.item.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

    private static final String header = "X-Sharer-User-Id";
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto create(@RequestHeader(name = header) @NotNull int userId,
                          @RequestBody @Valid ItemDto itemDto) {
        return itemService.create(userId, itemDto);
    }

    @PatchMapping(value = "/{itemId}")
    public ItemDto update(@RequestHeader(name = header) @NotNull int userId,
                          @PathVariable int itemId,
                          @RequestBody ItemDto itemDto) {
        return itemService.update(userId, itemId, itemDto);
    }

    @GetMapping(value = "/{itemId}")
    public ItemForResultDto getItem(@RequestHeader(name = header) @NotNull int userId,
                                    @PathVariable long itemId) {
        return itemService.getAll(userId,itemId);
    }

    @GetMapping
    public Collection<ItemForResultDto> getAllItems(@RequestHeader(name = header) int userId) {
        return itemService.getAllItems(userId);
    }

    @GetMapping(value = "/search")
    public Collection<ItemDto> search(@RequestParam("text") String text) {
        return itemService.search(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@Valid @RequestBody CommentDto commentDto,
                                    @RequestHeader(name = header) long userId,
                                    @PathVariable Long itemId)  {
        return itemService.createComment(commentDto, userId, itemId);

    }

}
