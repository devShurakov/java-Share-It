package com.example.ShareIt.request.mapper;

import com.example.ShareIt.request.dto.ItemRequestDto;
import com.example.ShareIt.request.model.ItemRequest;
import com.example.ShareIt.user.model.User;
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
