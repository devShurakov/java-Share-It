package com.example.ShareIt.item;

import com.example.ShareIt.exception.*;
import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.user.InMemoryUserStorage;
import com.example.ShareIt.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryItemStorage {
    public static final Map<Integer, Item> items = new HashMap<>();
    private int id = 1;

    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public InMemoryItemStorage(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public Item create(Item item) {
        Item newItem = new Item(id, item.getName(), item.getDescription(), item.isAvailable(), item.getOwner());
        if (newItem.isAvailable() == false) {
            throw new InvalidItemException("cannot be false by default");
        }
        if (userIsCreated(newItem) == false) {
            throw new UserNotFoundException("User is not found");
        } else {
            items.put(newItem.getId(), newItem);
            id++;
            log.info("field with type {} and id={} added.", newItem.getClass(), newItem.getId());
            return items.get(newItem.getId());
        }
    }

    public Item update(int userId, int itemId, ItemDto item) {
        if (userId != items.get(itemId).getOwner()) {
            throw new UserHaveNoAccessException("insufficient access rights");
        }
        if (item.getName() == null && item.getDescription() == null) {
            for (Item x : items.values()) {
                if (x.getId() == itemId) {
                    x.setAvailable(item.isAvailable());
                }
            }
        }
        if (item.getName() == null && item.getDescription() != null) {
            for (Item x : items.values()) {
                if (x.getId() == itemId) {
                    x.setDescription(item.getDescription());
                }
            }
        }
        if (item.getDescription() == null && item.getName() != null) {
            for (Item x : items.values()) {
                if (x.getId() == itemId) {
                    x.setName(item.getName());
                }
            }
        }
        if (item.getName() != null && item.getDescription() != null) {
            for (Item x : items.values()) {
                if (x.getId() == itemId) {
                    if (item.getName() != null) {
                        x.setName(item.getName());
                        x.setDescription(item.getDescription());
                        x.setAvailable(item.isAvailable());
                    }
                }
            }
        }
        log.info("field  with id={} updated: {}", item.getId(), item.getClass());
        return items.get(item.getId());
    }

    public boolean userIsCreated(Item item) {
        boolean checkUser = false;
        for (User x : inMemoryUserStorage.users.values()) {
            if (x.getId() == item.getOwner()) {
                checkUser = true;
            }
        }
        return checkUser;
    }

    public Item getItem(int userId) {
        if (userId < 0) {
            throw new InvalidItemException("The user ID cannot be negative.");
        }
        log.info("field  with id={}", userId);
        return items.get(userId);
    }

    public Collection<Item> getAllItems(int userId) {
        Collection<Item> newCollection = new ArrayList<>();
        for (Item x : items.values()) {
            if (x.getOwner() == userId) {
                newCollection.add(x);
            }
        }
        return newCollection;
    }

    public Collection<Item> search(String text) {
        Collection<Item> collection = new ArrayList<>();
        if (text.isEmpty() || text.isBlank()) {
            return collection;
        } else {
            String newText = text.toLowerCase();
            for (Item x : items.values()) {
                if (x.getName().toLowerCase().contains(newText) || x.getDescription().toLowerCase().contains(newText)) {
                    if (x.isAvailable() == true) {
                        collection.add(x);
                    }
                }
            }
            if (collection.isEmpty()) {
                return collection;
            } else {
                return collection;
            }
        }
    }
}
