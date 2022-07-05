package com.example.ShareIt.user;

import com.example.ShareIt.user.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(int userId, UserDto userDto);

    UserDto getUser(int userId);

    Collection<UserDto> getAllUsers();

    void delete(int userDtoId);
}
