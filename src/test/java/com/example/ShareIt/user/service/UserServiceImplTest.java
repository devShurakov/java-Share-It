package com.example.ShareIt.user.service;

import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.model.User;
import com.example.ShareIt.user.service.impl.UserServiceImpl;
import com.example.ShareIt.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImplTest {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;


    @Test
    void createUser() {
        UserDto userDto = new UserDto(1L, "Пётр", "some@email.com");

        userService.create(userDto);

        UserDto userDto1 = userService.getDtoUser(1L);

        assertThat(userDto1.getId(), notNullValue());
        assertThat(userDto1.getEmail(), equalTo(userDto.getEmail()));
        assertThat(userDto1.getName(), equalTo(userDto.getName()));
    }

    @Test
    void getUsers() {
        UserDto userDto2 = new UserDto(2L, "Иван", "test@email.com");

        userService.create(userDto2);

        Collection<UserDto> users = userService.getAllUsers();

        Assertions.assertEquals(2, users.size());
    }


    @Test
    void deleteUserById() {

        userService.delete(1);

        Optional<User> user = userRepository.findById(1L);

        Assertions.assertEquals(user, Optional.empty());
    }

}
