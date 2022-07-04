package com.example.ShareIt.user;

import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.model.User;

import java.util.Collection;

public interface UserServiceImpl {

    UserDto create(User userDto);

    UserDto update(int userId, User userDto);

    UserDto getUser(int userId);

    Collection<UserDto> getAllUsers();

    void delete(int userDtoId);
}
