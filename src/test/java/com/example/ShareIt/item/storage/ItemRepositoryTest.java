package com.example.ShareIt.item.storage;

import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private ItemRepository repository;

    @Test
    void givenStoredItem_whenFindItemsByOwner_thenCorrectListShouldBeReturned() {
        User owner = new User(null, "User name", "email@example.com");
        Item item = new Item(null, "Item name", "Item description", true, owner, null);
        em.persist(owner);
        em.persist(item);

        Collection<Item> result = repository.findAllItemsByOwnerIdOrderByIdAsc(owner.getId());

        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }

    @Test
    void givenStoredItem_whenSearchByName_thenCorrectListShouldBeReturned() {
        // Given
        User owner = new User(null, "User name", "email@example.com");
        Item item = new Item(null, "Item name", "Item description", true, owner, null);
        em.persist(owner);
        em.persist(item);

        // When
        Collection<Item> result = repository.search("name");
        // Then
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }
}
