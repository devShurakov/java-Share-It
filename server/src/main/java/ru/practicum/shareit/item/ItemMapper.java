package ru.practicum.shareit.item;

import ru.practicum.shareit.booking.Booking;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ItemMapper {
    public static ItemForResultDto mapToItemForResultDto(Item item) {

        ItemForResultDto itemForResult = new ItemForResultDto();

        itemForResult.setId(item.getId());
        itemForResult.setName(item.getName());
        itemForResult.setDescription(item.getDescription());
        itemForResult.setAvailable(item.getAvailable());
        itemForResult.setComments(null);

        if (item.getRequest() != null) {
            itemForResult.setRequestId(item.getRequest().getId());
        } else {
            itemForResult.setRequestId(null);
        }

        return itemForResult;
    }

    public Item mapToItem(ItemForResultDto itemDto) {

        Item item = new Item();

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

        ItemForResultDto.Booking item = new ItemForResultDto.Booking();

        if (booking == null) return null;

        item.setId(booking.getId());
        item.setBookerId(booking.getBooker().getId());

        return item;
    }

    public ItemForResultDto mapToItemForResultDtoUserBooking(Item item) {

        ItemForResultDto itemForResult = new ItemForResultDto();

        itemForResult.setId(item.getId());
        itemForResult.setName(item.getName());
        itemForResult.setDescription(item.getDescription());
        itemForResult.setAvailable(item.getAvailable());
        itemForResult.setComments(null);
        itemForResult.setLastBooking(null);
        itemForResult.setNextBooking(null);

        return itemForResult;
    }


}
