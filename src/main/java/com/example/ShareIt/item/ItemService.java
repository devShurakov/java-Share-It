package com.example.ShareIt.item;


import com.example.ShareIt.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto create(int userId, ItemDto itemDto);
    ItemDto update(int userId, int itemId, ItemDto itemDto);
    ItemDto getItem(int itemId);
    Collection<ItemDto> getAllItems(int userId);
    Collection<ItemDto> search(String text);
}
