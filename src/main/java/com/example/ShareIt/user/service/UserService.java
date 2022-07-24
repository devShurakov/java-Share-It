package com.example.ShareIt.user.service;

import com.example.ShareIt.user.dto.UserDto;
import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(int userId, UserDto userDto);

    UserDto getDtoUser(long userId);

    Collection<UserDto> getAllUsers();

    void delete(int userDtoId);
}
