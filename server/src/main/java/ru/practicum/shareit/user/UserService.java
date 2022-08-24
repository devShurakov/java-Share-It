package ru.practicum.shareit.user;

import ru.practicum.shareit.user.UserDto;
import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(int userId, UserDto userDto);

    UserDto getDtoUser(long userId);

    Collection<UserDto> getAllUsers();

    void delete(int userDtoId);
}
