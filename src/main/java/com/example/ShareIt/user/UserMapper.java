package com.example.ShareIt.user;

import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMapper {
    public static User mapToUser(User userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static User mapToUser(User userDto, int userId) {
        User user = new User();
        user.setId(userId);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static Collection<UserDto> maptoAllUserDto(Collection<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(mapToUserDto(user));
        }
        return dtos;
    }


}
