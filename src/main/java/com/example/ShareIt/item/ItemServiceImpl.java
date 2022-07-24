package com.example.ShareIt.item;

import com.example.ShareIt.exception.InvalidItemException;
import com.example.ShareIt.exception.UserHaveNoAccessException;
import com.example.ShareIt.exception.UserNotFoundException;
import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.user.InMemoryUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ItemServiceImpl  implements ItemService {
    private int id = 1;
    private final InMemoryItemStorage inMemoryItemStorage;
    private final InMemoryUserStorage inMemoryUserStorage;
    private final ItemMapper itemMapper;
    @Autowired
    public ItemServiceImpl(InMemoryItemStorage inMemoryItemStorage, ItemMapper itemMapper, InMemoryUserStorage inMemoryUserStorage ) {
        this.inMemoryItemStorage = inMemoryItemStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.itemMapper = itemMapper;
    }
    public ItemDto create(int userId, ItemDto itemDto) {
        if(inMemoryUserStorage.getUser(userId) == null){
            throw new UserNotFoundException("User is not found");
        }else if (itemDto.getAvailable() == null) {
            throw new InvalidItemException("cannot be false by default");
        } else{
            Item newItem  = makeItem(itemMapper.mapToItem(itemDto, userId));
            Item item =  inMemoryItemStorage.create(newItem);
            return itemMapper.mapToItemDto(item);
        }
    }
    public Item makeItem(Item item) {
        Item newItem = new Item(id, item.getName(), item.getDescription(), item.getAvailable(), item.getOwner());
        id++;
        return newItem;
    }
    public ItemDto update(int userId, int itemId, ItemDto itemDto) {
        if (userId != inMemoryItemStorage.items.get(itemId).getOwner().getId()) {
            throw new UserHaveNoAccessException("insufficient access rights");
        }

        Item item = inMemoryItemStorage.getItem(itemId);
        inMemoryItemStorage.update(userId, itemId, itemDto);
        return itemMapper.mapToItemDto(item);
    }
    public ItemDto getItem(int itemId) {
        if (itemId < 0) {
            throw new InvalidItemException("The user ID cannot be negative.");
        }
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