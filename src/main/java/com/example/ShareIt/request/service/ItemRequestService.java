package com.example.ShareIt.request.service;

import com.example.ShareIt.item.dto.ItemForResultDto;
import com.example.ShareIt.item.exception.ItemNotFoundException;
import com.example.ShareIt.item.mapper.ItemMapper;
import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.item.storage.ItemRepository;
import com.example.ShareIt.request.dto.ItemRequestDto;
import com.example.ShareIt.request.mapper.ItemRequestMapper;
import com.example.ShareIt.request.model.ItemRequest;
import com.example.ShareIt.request.storage.ItemRequestRepository;
import com.example.ShareIt.user.model.User;
import com.example.ShareIt.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemRequestService {

    final private ItemRequestMapper itemRequestMapper;

    final private ItemRequestRepository itemRequestRepository;

    final private UserServiceImpl userService;

   final private ItemRepository itemRepository;


    @Autowired
    public ItemRequestService(ItemRequestMapper itemRequestMapper, ItemRequestRepository itemRequestRepository
            , UserServiceImpl userService, ItemRepository itemRepository) {

        this.itemRequestMapper = itemRequestMapper;

        this.itemRequestRepository = itemRequestRepository;

        this.userService = userService;

        this.itemRepository = itemRepository;
    }

    public ItemRequestDto create(int userId, ItemRequestDto itemRequestDto) {

        userService.getUser(userId);
        User request = userService.getUser(userId);

        ItemRequest ItemRequest = itemRequestMapper.toItemRequest(itemRequestDto);

        ItemRequest.getRequest().setId(userService.getUser(userId).getId());
        ItemRequest.getRequest().setName(userService.getUser(userId).getName());
        ItemRequest.getRequest().setEmail(userService.getUser(userId).getEmail());
        ItemRequest.setRequest(request);

        return itemRequestMapper.toItemRequestDto(itemRequestRepository.save(ItemRequest));
    }

    public Collection<ItemRequestDto> getRequest(long requestId) {

        userService.getUser(requestId);
        List<ItemRequestDto> itemRequest =  itemRequestRepository.findAllByRequestIdOrderByCreatedDesc(requestId)
                .stream()
                .map(this::searchItemsByRequest)
                .collect(Collectors.toList());

                return itemRequest;
    }

    public ItemRequest getRequestById(long requestId) {

        userService.getUser(requestId);

        return itemRequestRepository.findById(requestId).orElseThrow(() ->
                new ItemNotFoundException(String.format("Entity with id not found")));
    }

    public Collection<ItemRequestDto>  getAllRequest(long userId, int from, int size) {

        if (from < 0) {
            throw new ValidationException(String.format("Incorrect value for from %d.", from));
        }
        if (size < 1) {
            throw new ValidationException(String.format("Incorrect value for size %d.", size));
        }
        Pageable page = PageRequest.of(from, size);

        Collection<ItemRequestDto> itemCollection =  toItemRequestDtoCollections2(itemRequestRepository
                .findAllByRequest_IdIsNotOrderByCreatedDesc(userId, page));

        return itemCollection;

    }



    public ItemRequestDto getItemRequestById(long userId, long requestId) {

        userService.getUser(userId);
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow(() ->
                new NoSuchElementException(String.format("request with this id%d.", requestId)));

        return searchItemsByRequest(itemRequest);
    }

    private ItemRequestDto searchItemsByRequest(ItemRequest request) {

        List<ItemForResultDto> items = itemRepository.findAllByRequestId(request.getId())
                .stream()
                .map((Item item) -> ItemMapper.mapToItemForResultDto(item))
                .collect(Collectors.toList());

        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(request);
        itemRequestDto.setItems(items);

        return itemRequestDto;
    }


    public List<ItemRequestDto> toItemRequestDtoCollections2(Collection<ItemRequest> collection) {

        List<ItemRequestDto> listOfRequests = new ArrayList<>();

        for(ItemRequest x: collection){
            List<ItemForResultDto> items = itemRepository.findAllByRequestId(x.getId())
                    .stream()
                    .map((Item item) -> ItemMapper.mapToItemForResultDto(item))
                    .collect(Collectors.toList());

            ItemRequestDto forResult = itemRequestMapper.toItemRequestDto(x);
            forResult.setItems(items);
            listOfRequests.add(forResult);
        }

        return listOfRequests;
    }

}
