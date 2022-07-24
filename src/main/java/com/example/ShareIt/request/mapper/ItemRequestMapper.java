package com.example.ShareIt.request.mapper;

import com.example.ShareIt.request.dto.ItemRequestDto;
import com.example.ShareIt.request.model.ItemRequest;
import com.example.ShareIt.user.model.User;

public class ItemRequestMapper {
    public ItemRequest toItemRequest(ItemRequestDto itemDto) {
        ItemRequestDto.User user = itemDto.getRequest();
        return new ItemRequest(itemDto.getId(), itemDto.getDescription(),
                new User(user.getId(), user.getName(), null), itemDto.getCreated());
    }

    public ItemRequestDto toItemDto(ItemRequest itemRequest) {
        User user = itemRequest.getRequest();
        return new ItemRequestDto(itemRequest.getId(), itemRequest.getDescription(),
                new ItemRequestDto.User(user.getId(), user.getName()), itemRequest.getCreated());
    }
}
