package com.example.ShareIt.user.mapper;

import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserMapper {
    public static User mapToUser(UserDto userDto) {

        User user = new User();
        user.setId(userDto.getId());
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
