package com.example.ShareIt.item.mapper;

import com.example.ShareIt.booking.model.Booking;
import com.example.ShareIt.user.exception.InvalidUserIdException;
import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.dto.ItemForResultDto;
import com.example.ShareIt.item.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ItemMapper {
    public static ItemForResultDto mapToItemForResultDto(Item item) {
        ItemForResultDto itemForResultDto = new ItemForResultDto();
        itemForResultDto.setId(item.getId());
        itemForResultDto.setName(item.getName());
        itemForResultDto.setDescription(item.getDescription());
        itemForResultDto.setAvailable(item.getAvailable());
        itemForResultDto.setComments(null);
        return itemForResultDto;
    }

    public Item mapToItem(ItemDto itemDto, int userId) {
        Item item = new Item();
        if (String.valueOf(itemDto.getAvailable()).isEmpty()) {
            throw new InvalidUserIdException("cannot be null");
        }
        if (itemDto.getName().isEmpty() || itemDto.getDescription().isEmpty()) {
            throw new InvalidUserIdException("cannot be null");
        }
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        return item;
    }

    public ItemDto mapToItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }

    public Collection<ItemDto> maptoAllItemDto(Collection<Item> item) {
        List<ItemDto> dtos = new ArrayList<>();
        for (Item x : item) {
            dtos.add(mapToItemDto(x));
        }
        return dtos;
    }

    public ItemForResultDto.Booking mapToItemForResultDtoBooking(Booking booking) {
        ItemForResultDto.Booking ItemForResultDto = new ItemForResultDto.Booking();
        if(booking == null){
            return null;
        }
        ItemForResultDto.setId(booking.getId());
        ItemForResultDto.setBookerId(booking.getBooker().getId());
        return  ItemForResultDto;
    }

    public ItemForResultDto mapToItemForResultDtoUserBooking(Item item) {
        ItemForResultDto ItemForResultDto = new ItemForResultDto();
        ItemForResultDto.setId(item.getId());
        ItemForResultDto.setName(item.getName());
        ItemForResultDto.setDescription(item.getDescription());
        ItemForResultDto.setAvailable(item.getAvailable());
        ItemForResultDto.setComments(null);
        ItemForResultDto.setLastBooking(null);
        ItemForResultDto.setNextBooking(null);
        return  ItemForResultDto;
    }

}
