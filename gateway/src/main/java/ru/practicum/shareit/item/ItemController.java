package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.Collections;

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
        checkData(itemForResultDto);
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
        checkPageBorders(from, size);
        return itemClient.getAllItems(userId,from, size);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> search(@RequestParam("text") String text,
                                      @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {
        if (text.isBlank()) {
            return new ResponseEntity<Object>(Collections.emptyList(), HttpStatus.OK);
        }
        checkPageBorders(from, size);

        return itemClient.search(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto commentDto,
                                    @RequestHeader(name = header) int userId,
                                    @PathVariable int itemId)  {

        return itemClient.createComment(commentDto, userId, itemId);

    }

    private void checkPageBorders(int from, int size) {
        if (from < 0) {
            throw new ValidationException(String.format("недопустимое значение from %d.", from));
        }
        if (size < 1) {
            throw new ValidationException(String.format("недопустимое значение size %d.", size));
        }
    }

    private void checkData(ItemForResultDto itemForResultDto) {
        if (itemForResultDto.getName() == null || itemForResultDto.getName().isBlank()) {
            throw new ValidationException("item.Name is null or Blank");
        }
        if (itemForResultDto.getDescription() == null || itemForResultDto.getDescription().isBlank()) {
            throw new ValidationException("item.Description is null or Blank");
        }
        if (itemForResultDto.getAvailable() == null) {
            throw new ValidationException("item.isAvailable is null or Blank");
        }
    }
}
