package ru.practicum.shareit.item;

import java.util.Collection;

public interface ItemService {

    ItemForResultDto create(int userId, ItemForResultDto itemForResultDto);

    ItemDto update(int userId, int itemId, ItemDto itemDto);

    Item getItem(long itemId);

    ItemForResultDto getAll(int userId, long itemId);

    Collection<ItemForResultDto> getAllItems(long userId, int from, int size);

    Collection<ItemDto> search(String text, int from, int size);

    CommentDto createComment(CommentDto commentDto, long userId, Long itemId);

    Collection<Item> searchAvailableItems(String text);
}
