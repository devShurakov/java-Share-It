package com.example.ShareIt.item.service.impl;

import com.example.ShareIt.booking.dto.BookingDto;
import com.example.ShareIt.booking.mapper.BookingMapper;
import com.example.ShareIt.booking.model.Booking;
import com.example.ShareIt.booking.model.BookingStatus;
import com.example.ShareIt.booking.storage.BookingRepository;
import com.example.ShareIt.item.dto.CommentDto;
import com.example.ShareIt.item.dto.ItemForResultDto;
import com.example.ShareIt.item.exception.ItemDoesNotBelongException;
import com.example.ShareIt.item.exception.ItemNotFoundException;
import com.example.ShareIt.item.exception.UserNotBookedItemException;
import com.example.ShareIt.item.mapper.CommentMapper;
import com.example.ShareIt.item.model.Comment;
import com.example.ShareIt.item.service.ItemService;
import com.example.ShareIt.item.mapper.ItemMapper;
import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.item.storage.CommentRepository;
import com.example.ShareIt.item.storage.ItemRepository;
import com.example.ShareIt.user.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final CommentMapper commentMapper;
    private final BookingMapper bookingMapper;
    private final CommentRepository commentRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper, UserService userService, BookingRepository bookingRepository, CommentMapper commentMapper, CommentRepository commentRepository, BookingMapper bookingMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.bookingMapper = bookingMapper;
    }

    public ItemDto create(int userId, ItemDto itemDto) {
        Item item = itemMapper.mapToItem(itemDto, userId);
        item.setOwner(userService.getUser(userId));
        return itemMapper.mapToItemDto(itemRepository.save(item));
    }

    public ItemDto update(int userId, int itemId, ItemDto itemDto) {
        Item item = itemRepository.findById((long) itemId).orElseThrow(() -> {
            return new ItemNotFoundException(String.format("Entity with id %d not found", itemDto.getId()));
        });
        if (check((long) userId, item) == false) {
            throw new ItemDoesNotBelongException("Item doesn't belong User");
        }
        if (itemDto.getAvailable() != null) {
            log.info("Update availability on {}", item.getAvailable());
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getDescription() != null) {
            log.info("Update description on {}", item.getDescription());
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getName() != null) {
            log.info("Update name on {}", item.getName());
            item.setName(itemDto.getName());
        }
        return itemMapper.mapToItemDto(itemRepository.save(item));
    }

    public ItemForResultDto getItemDto(int userId, long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            return new ItemNotFoundException(String.format("Entity with id %d not found", itemId));
        });
        LocalDateTime now = LocalDateTime.now();
        ItemForResultDto itemForResultDto = itemMapper.mapToItemForResultDto(item);
            itemForResultDto.setComments(commentMapper.mapToCommentDtoCollection(commentRepository.findAllByItem_Id(itemId)));
        if (item.getOwner().getId() == userId) {
            itemForResultDto.setNextBooking(getLastBooking(itemId, now));
            itemForResultDto.setLastBooking(getPreviousBooking(itemId, now));
            itemForResultDto.setComments(commentMapper.mapToCommentDtoCollection(commentRepository.findAllByItem_Id(itemId)));
            return itemForResultDto;
        }
        ItemForResultDto itemForResultDto2 = itemMapper.mapToItemForResultDtoUserBooking(item);
        itemForResultDto2.setComments(commentMapper.mapToCommentDtoCollection(commentRepository.findAllByItem_Id(itemId)));
        return itemForResultDto2;
    }

    public ItemForResultDto.Booking getPreviousBooking(long itemId, LocalDateTime now) {
        return itemMapper.mapToItemForResultDtoBooking(bookingRepository.findBookingsByItemIdAndEndBeforeOrderByStartDesc(itemId, now).stream().max(Comparator.comparing(Booking::getEnd)).orElse(null));
    }

    public ItemForResultDto.Booking getLastBooking(long itemId, LocalDateTime now) {
        return itemMapper.mapToItemForResultDtoBooking(bookingRepository.findBookingsByItemIdAndStartAfterOrderByStartDesc(itemId, now).stream().findFirst().orElse(null));
    }

    public Item getItem(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> {
            return new ItemNotFoundException(String.format("Entity with id %d not found", itemId));
        });
    }

    public Collection<ItemForResultDto> getAllItems(int userId) {
        Collection<Item> userItems = itemRepository.findAll().stream().filter(item -> item.getOwner().getId() == userId).collect(Collectors.toList());
        LocalDateTime now = LocalDateTime.now();
        Collection returnColl = new ArrayList();
        for (Item x : userItems) {
            ItemForResultDto itemForResultDto = itemMapper.mapToItemForResultDto(x);
            itemForResultDto.setNextBooking(getLastBooking(x.getId(), now));
            itemForResultDto.setLastBooking(getPreviousBooking(x.getId(), now));
            returnColl.add(itemForResultDto);
        }
        return returnColl;
    }

    public CommentDto createComment(CommentDto commentDto, long userId, Long itemId) {
        Collection<BookingDto> dtos = getByUserAndItem(userId, itemId)
                .stream()
                .filter(i -> i.getStatus() == BookingStatus.APPROVED)
                .filter(i -> i.getEnd().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        if (dtos.size() == 0) {
            throw new UserNotBookedItemException("The user has not rented this item");
        }
        Comment comment = commentMapper.mapToComment(commentDto);
        comment.setItem(getItem(itemId));
        comment.setAuthor(userService.getUser(userId));
        comment.setCreated(LocalDateTime.now());
        return commentMapper.mapToCommentDto(commentRepository.save(comment),
                userService.getUser(userId).getName());
    }

    @Transactional
    public List<BookingDto> getByUserAndItem(long userId, long itemId) {
        return bookingMapper.maptoAllBookingToDto(bookingRepository.findAllByBooker_IdAndItem_Id(userId, itemId));
    }

    public Collection<ItemDto> search(String text) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemMapper.maptoAllItemDto(itemRepository.search(text));
    }

    private boolean check(Long userId, Item item) {
        if (item.getOwner().getId().equals(userId)) {
            return true;
        }
        return false;
    }
}
