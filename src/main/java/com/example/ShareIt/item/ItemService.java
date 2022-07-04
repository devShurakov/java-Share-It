package com.example.ShareIt.item;

import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ItemService implements ItemServiceImpl {
    private final InMemoryItemStorage inMemoryItemStorage;
    private final ItemMapper itemMapper;
    @Autowired
    public ItemService(InMemoryItemStorage inMemoryItemStorage, ItemMapper itemMapper ) {
        this.inMemoryItemStorage = inMemoryItemStorage;
        this.itemMapper = itemMapper;
    }
    public ItemDto create(int userId, ItemDto itemDto) {
        Item item = inMemoryItemStorage.create(itemMapper.mapToItem(itemDto, userId));;
        return itemMapper.mapToItemDto(item);
    }
    public ItemDto update(int userId, int itemId, ItemDto itemDto) {
        Item item = inMemoryItemStorage.getItem(itemId);
        inMemoryItemStorage.update(userId, itemId, itemDto);
        return itemMapper.mapToItemDto(item);
    }
    public ItemDto getItem(int itemId) {
        Item item = inMemoryItemStorage.getItem(itemId);
        return itemMapper.mapToItemDto(item);
    }
    public Collection<ItemDto> getAllItems(int userId) {
        Collection<Item> userItems = inMemoryItemStorage.getAllItems(userId);
        return itemMapper.maptoAllItemDto(userItems);
    }
    public Collection<ItemDto> search(String text) {
        Collection<Item> item = inMemoryItemStorage.search(text);
        return itemMapper.maptoAllItemDto(item);
    }

}
