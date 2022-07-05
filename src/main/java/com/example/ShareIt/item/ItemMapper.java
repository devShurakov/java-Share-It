package com.example.ShareIt.item;

import com.example.ShareIt.exception.InvalidUserIdException;
import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.user.InMemoryUserStorage;
import com.example.ShareIt.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ItemMapper {
    private final InMemoryUserStorage inMemoryUserStorage;
    @Autowired
    public ItemMapper(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }
    public Item mapToItem(ItemDto itemDto, int userId) {
        Item item = new Item();
        if (String.valueOf(itemDto.isAvailable()).isEmpty()) {
            throw new InvalidUserIdException("cannot be null");
        }
        if (itemDto.getName().isEmpty() || itemDto.getDescription().isEmpty()) {
            throw new InvalidUserIdException("cannot be null");
        }
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.isAvailable());
        item.setOwner(inMemoryUserStorage.getUser(userId));
        return item;
    }
    public ItemDto mapToItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.isAvailable());
        return itemDto;
    }
    public Collection<ItemDto> maptoAllItemDto(Collection<Item> item) {
        List<ItemDto> dtos = new ArrayList<>();
        for (Item x : item) {
            dtos.add(mapToItemDto(x));
        }
        return dtos;
    }

}
