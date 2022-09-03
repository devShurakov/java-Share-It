package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ItemForResultDto create(@RequestHeader(name = header) int userId,
                          @RequestBody @Valid ItemForResultDto itemForResultDto) {

        return itemService.create(userId, itemForResultDto);
    }

    @PatchMapping(value = "/{itemId}")
    public ItemDto update(@RequestHeader(name = header) int userId,
                          @PathVariable int itemId,
                          @RequestBody ItemDto itemDto) {

        return itemService.update(userId, itemId, itemDto);
    }

    @GetMapping(value = "/{itemId}")
    public ItemForResultDto getItem(@RequestHeader(name = header) int userId,
                                    @PathVariable long itemId) {

        return itemService.getAll(userId,itemId);
    }

    @GetMapping
    public Collection<ItemForResultDto> getAllItems(@RequestHeader(name = header) long userId,
                                                    @RequestParam(defaultValue = "0") int from,
                                                    @RequestParam(defaultValue = "10") int size) {

        return itemService.getAllItems(userId,from, size);
    }

    @GetMapping(value = "/search")
    public Collection<ItemDto> search(@RequestParam("text") String text,
                                      @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {
        return itemService.search(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@Valid @RequestBody CommentDto commentDto,
                                    @RequestHeader(name = header) long userId,
                                    @PathVariable Long itemId)  {

        return itemService.createComment(commentDto, userId, itemId);

    }

}
