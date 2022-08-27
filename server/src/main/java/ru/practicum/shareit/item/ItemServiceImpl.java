package ru.practicum.shareit.item;

import ru.practicum.shareit.booking.BookingDto;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.exception.ItemDoesNotBelongException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.UserNotBookedItemException;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.user.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.exception.InvalidUserIdException;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final UserServiceImpl userService;

    private final BookingRepository bookingRepository;

    private final CommentMapper commentMapper;

    private final BookingMapper bookingMapper;

    private final CommentRepository commentRepository;

    private final ItemRequestService itemRequestService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper, UserServiceImpl userService,
                           BookingRepository bookingRepository, CommentMapper commentMapper,
                           CommentRepository commentRepository, BookingMapper bookingMapper,
                           ItemRequestService itemRequestService) {

        this.itemRepository = itemRepository;

        this.itemMapper = itemMapper;

        this.userService = userService;

        this.bookingRepository = bookingRepository;

        this.commentMapper = commentMapper;

        this.commentRepository = commentRepository;

        this.bookingMapper = bookingMapper;

        this.itemRequestService = itemRequestService;
    }


    public ItemForResultDto create(int userId, ItemForResultDto itemForResultDto) {

        if (itemForResultDto.getAvailable() == null) {
            throw new InvalidUserIdException("Cannot be null");
        }

        if (itemForResultDto.getName().isEmpty()) {
            throw new InvalidUserIdException("Cannot be null");
        }

        ItemRequest request = null;
        if (itemForResultDto.getRequestId() != null) {
            request = itemRequestService.getRequestById(itemForResultDto.getRequestId());
        }

        Item item = itemMapper.mapToItem(itemForResultDto);
        item.setOwner(userService.getUser(userId));
        item.setRequest(request);
        if (itemForResultDto.getRequestId() != null) {
            item.setRequest(itemRequestService.getRequestById(itemForResultDto.getRequestId()));
        }

        return itemMapper.mapToItemForResultDto(itemRepository.save(item));
    }

    public ItemDto update(int userId, int itemId, ItemDto itemDto) {

        Item item = itemRepository.findById((long) itemId).orElseThrow(() ->
                new ItemNotFoundException(String.format("Entity with id %d not found", itemDto.getId())));

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

    public ItemForResultDto getAll(int userId, long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new ItemNotFoundException(String.format("Entity with id %d not found", itemId)));

        LocalDateTime now = LocalDateTime.now();

        ItemForResultDto itemForOwner = itemMapper.mapToItemForResultDto(item);
        itemForOwner.setComments(commentMapper
                .mapToCommentDtoCollection(commentRepository.findAllByItem_Id(itemId)));

        if (item.getOwner().getId() == userId) {
            itemForOwner.setNextBooking(getLastBooking(itemId, now));
            itemForOwner.setLastBooking(getPreviousBooking(itemId, now));
            itemForOwner.setComments(commentMapper
                    .mapToCommentDtoCollection(commentRepository.findAllByItem_Id(itemId)));

            return itemForOwner;
        }

        ItemForResultDto itemForUser = itemMapper.mapToItemForResultDtoUserBooking(item);
        itemForUser.setComments(commentMapper
                .mapToCommentDtoCollection(commentRepository.findAllByItem_Id(itemId)));

        return itemForUser;
    }

    private ItemForResultDto.Booking getPreviousBooking(long itemId, LocalDateTime now) {

        return itemMapper.mapToItemForResultDtoBooking(bookingRepository
                .findBookingsByItemIdAndEndBeforeOrderByStartDesc(itemId, now)
                .stream()
                .max(Comparator.comparing(Booking::getEnd))
                .orElse(null));
    }

    private ItemForResultDto.Booking getLastBooking(long itemId, LocalDateTime now) {

        return itemMapper.mapToItemForResultDtoBooking(bookingRepository
                .findBookingsByItemIdAndStartAfterOrderByStartDesc(itemId, now)
                .stream().findFirst().orElse(null));
    }

    public Item getItem(long itemId) {

        return itemRepository.findById(itemId).orElseThrow(() ->
                new ItemNotFoundException(String.format("Entity with id %d not found", itemId)));
    }

    public Collection<ItemForResultDto> getAllItems(long userId, int from, int size) {

        Pageable page = PageRequest.of(from, size);

        List<Item> userItems = itemRepository.findAllItemsByOwnerIdOrderByIdAsc(userId, page)

                .stream()
                .filter(item -> item.getOwner().getId() == userId)
                .collect(Collectors.toList());

        LocalDateTime now = LocalDateTime.now();

        Collection returnColl = new ArrayList();

        for (Item x : userItems) {
            ItemForResultDto item = itemMapper.mapToItemForResultDto(x);
            item.setNextBooking(getLastBooking(x.getId(), now));
            item.setLastBooking(getPreviousBooking(x.getId(), now));
            returnColl.add(item);
        }


        return returnColl;
    }

    public CommentDto createComment(CommentDto commentDto, long userId, Long itemId) {

        Collection<BookingDto> dtos = getByUserAndItem(userId, itemId)
                .stream()
                .filter(i -> i.getStatus().contains(BookingStatus.APPROVED.name()))
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

    @Override
    public Collection<ItemDto> search(String text, int from, int size) {

        Pageable page = checkPageBorders(from, size);

        if (text.isBlank()) {
            return Collections.emptyList();
        }

        return itemMapper.maptoAllItemDto(itemRepository.search(text, page));
    }

    @Override
    public Collection<Item> searchAvailableItems(String text) {

        if (text.isBlank()) {
            return Collections.emptyList();
        }

        return itemRepository.search(text);
    }

    private boolean check(Long userId, Item item) {

        if (item.getOwner().getId().equals(userId)) {
            return true;
        }

        return false;
    }

    private Pageable checkPageBorders(int from, int size) {
        if (from < 0) {
            throw new ValidationException(String.format("incorrect value for from %d.", from));
        }
        if (size < 1) {
            throw new ValidationException(String.format("incorrect value for size %d.", size));
        }
        return PageRequest.of(from, size);
    }
}
