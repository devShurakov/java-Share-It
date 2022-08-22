package com.example.ShareIt.item.storage;

import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Collection;
import java.util.List;


@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private TestEntityManager em;

    User user = new User(1L, "Kis", "kis@mail.ru");

    @Test
    void storedItem_findAllItemsByOwnerIdOrderByIdAsc_Test() {
        User owner = new User(null, "User name", "email@example.com");
        Item item = new Item(null, "Item name", "Item description", true, owner, null);
        em.persist(owner);
        em.persist(item);

        Collection<Item> result = itemRepository.findAllItemsByOwnerIdOrderByIdAsc(owner.getId());

        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }

    @Test
    void storedItem_search_Test() {
        User owner = new User(null, "User name", "email@example.com");
        Item item = new Item(null, "Item name", "Item description", true, owner, null);
        em.persist(owner);
        em.persist(item);

        Collection<Item> result = itemRepository.search("name");

        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }

}
