package com.example.ShareIt.item;

import com.example.ShareIt.item.dto.ItemDto;
import com.example.ShareIt.item.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryItemStorage {
    public static final Map<Integer, Item> items = new HashMap<>();

    public Item create(Item item) {
        items.put(item.getId(), item);
        log.info("field with type {} and id={} added.", item.getClass(), item.getId());
        return items.get(item.getId());
    }
    public Item update(int userId, int itemId, ItemDto item) {
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
    public Item getItem(int itemId) {
        log.info("field  with id={}", itemId);
        return items.get(itemId);
    }
    public Collection<Item> getAllItems(int userId) {
        Collection<Item> newCollection = new ArrayList<>();
        for (Item x : items.values()) {
            if (x.getOwner().getId() == userId) {
                newCollection.add(x);
            }
        }
        log.info("All items received");
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
                log.info("Item={} not found", text);
                return collection;
            } else {
                log.info("Item={} found", text);
                return collection;

            }
        }
    }
}
