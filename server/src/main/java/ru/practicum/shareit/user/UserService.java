package ru.practicum.shareit.user;

import org.springframework.http.HttpStatus;

import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(int userId, UserDto userDto);

    UserDto getDtoUser(long userId);

    Collection<UserDto> getAllUsers();

    HttpStatus delete(int userId);
}
