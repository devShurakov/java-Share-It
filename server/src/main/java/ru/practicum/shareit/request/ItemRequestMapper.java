package ru.practicum.shareit.request;

import ru.practicum.shareit.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class ItemRequestMapper {


    public ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {

        return new ItemRequest(itemRequestDto.getId(), itemRequestDto.getDescription(),
                new User(null, null, null), itemRequestDto.getCreated(), new ArrayList<>() {
        });
    }

    public ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {

        return new ItemRequestDto(itemRequest.getId(), itemRequest.getDescription(),
                new ItemRequestDto.User(itemRequest.getRequest().getId(), itemRequest.getRequest().getName()),
                itemRequest.getCreated(), new ArrayList<>());
    }


}
