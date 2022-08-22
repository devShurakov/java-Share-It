package com.example.ShareIt.item.service;

import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.item.storage.ItemRepository;
import com.example.ShareIt.user.model.User;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemServiceImplTest {

    private final EntityManager em;
    private final ItemRepository itemRepository;

    private final User user = new User(1L, "User", "user@mail.com");
    private final Item item = new Item(1L, "Item", "Test", true, user, null);
    private final User userAdd = new User(null, "User", "user@mail.com");
    private final Item itemAdd = new Item(null, "Item", "Test", true, user, null);
    private final ItemDto itemExpected = new ItemDto(1L, "Item 1", "Test", true);
    private final Item itemAdd1 = new Item(null, "Item 1", "Test", true, user, null);
    private final Item itemAdd2 = new Item(null, "Item 2", "Test", true, user, null);
    private final ItemDto itemExpected1 = new ItemDto(1L, "Item 1", "Test", true);
    private final ItemDto itemExpected2 = new ItemDto(2L, "Item 2", "Test",true);

    @Test
    void getOwnerItems() {
        User otherUser = new User(2L, "User2", "user2@mail.com");
        User otherUserAdd = new User(null, "User2", "user2@mail.com");
        Item itemAddOtherUser = new Item(null, "Item", "Test", true, otherUser, null);
        em.persist(userAdd);
        em.persist(otherUserAdd);
        em.persist(itemAdd1);
        em.persist(itemAdd2);
        em.persist(itemAddOtherUser);

        List<ItemDto> itemsExpected = List.of(itemExpected1, itemExpected2);

        Pageable page = PageRequest.of(0, 10);

        List<Item> items = itemRepository.findAllItemsByOwnerIdOrderByIdAsc(1L, page);
        List<ItemDto> itemsDto = mapCollectionToDto(items);

        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals(itemsExpected.get(0), itemsDto.get(0));
        assertEquals(itemsExpected.get(1), itemsDto.get(1));
    }

        public List<ItemDto> mapCollectionToDto(List<Item> item) {
        List<ItemDto> toReturn = new ArrayList();

        for (Item x: item) {
            toReturn.add(mapToItemDto(x));
        }

        return toReturn;
    }

        public ItemDto mapToItemDto(Item item) {
        ItemDto itemDto = new ItemDto();

        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());

        return itemDto;
    }
}


