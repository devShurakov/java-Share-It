package com.example.ShareIt.item.repository;


import com.example.ShareIt.item.model.Item;
import com.example.ShareIt.item.storage.ItemRepository;
import com.example.ShareIt.user.model.User;
import com.example.ShareIt.user.storage.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class temRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    User user = new User(1l, "Denis", "Denis@mail.ru");

    Item itemDto1 = new Item(
            1L,
            "Отвертка хорошая",
            "Крестовая",
            true,
            user,
            null);

    Item itemDto2 = new Item(
            2L,
            "Отвертка",
            "Крестовая, для закручивания устройств соединений с крестовой шляпкой",
            true,
            user,
            null);


    @Test
    void search() {

        userRepository.save(user);
        itemRepository.save(itemDto1);

        itemRepository.save(itemDto2);

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(0, 5, sort);

        List<Item> items = itemRepository.search("отв", page);

        assertThat(items).isNotEmpty();
        assertThat(items).hasSize(2);

    }
}

