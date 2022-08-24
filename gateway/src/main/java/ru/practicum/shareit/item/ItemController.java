package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@Validated
@RequestMapping(value = "/items")
public class ItemController {

    private static final String header = "X-Sharer-User-Id";

    private final ItemClient itemClient;

    @Autowired
    public ItemController(ItemClient itemClient) {
        this.itemClient = itemClient;
    }


    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(name = header) @NotNull int userId,
                                         @RequestBody @Valid ItemForResultDto itemForResultDto) {

        return itemClient.create(userId, itemForResultDto);
    }

    @PatchMapping(value = "/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader(name = header) @NotNull int userId,
                          @PathVariable int itemId,
                          @RequestBody ItemDto itemDto) {

        return itemClient.update(userId, itemId, itemDto);
    }

    @GetMapping(value = "/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader(name = header) @NotNull int userId,
                                    @PathVariable int itemId) {

        return itemClient.getAll(userId,itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItems(@RequestHeader(name = header) long userId,
                                                    @RequestParam(defaultValue = "0") int from,
                                                    @RequestParam(defaultValue = "10") int size) {

        return itemClient.getAllItems(userId,from, size);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> search(@RequestParam("text") String text,
                                      @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {

        return itemClient.search(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto commentDto,
                                    @RequestHeader(name = header) int userId,
                                    @PathVariable int itemId)  {

        return itemClient.createComment(commentDto, userId, itemId);

    }

}
