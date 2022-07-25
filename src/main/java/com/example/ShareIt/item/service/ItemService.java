package com.example.ShareIt.item.service;


import com.example.ShareIt.item.dto.CommentDto;
import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.dto.ItemForResultDto;
import com.example.ShareIt.item.model.Item;

import java.util.Collection;

public interface ItemService {

    ItemDto create(int userId, ItemDto itemDto);

    ItemDto update(int userId, int itemId, ItemDto itemDto);

    Item getItem(long itemId);

    ItemForResultDto getAll(int userId, long itemId);

    Collection<ItemForResultDto> getAllItems(int userId);

    Collection<ItemDto> search(String text);

    CommentDto createComment(CommentDto commentDto, long userId, Long itemId);

}
