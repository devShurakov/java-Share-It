package com.example.ShareIt.user;

import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService implements UserServiceImpl{
    private final InMemoryUserStorage inMemoryUserStorage;
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }
    public UserDto create(User userDto) {
        User user = inMemoryUserStorage.create(UserMapper.mapToUser(userDto));
        return UserMapper.mapToUserDto(user);
    }
    public UserDto update(int userId, User userDto) {
        User user = inMemoryUserStorage.update(UserMapper.mapToUser(userDto, userId));
        return UserMapper.mapToUserDto(user);
    }
    public UserDto getUser(int userId) {
        User user = inMemoryUserStorage.getUser(userId);
        return UserMapper.mapToUserDto(user);
    }
    public Collection<UserDto> getAllUsers() {
        Collection<User> userItems = inMemoryUserStorage.getAllUsers();
        return UserMapper.maptoAllUserDto(userItems);
    }
    public void delete(int userDtoId) {
        inMemoryUserStorage.delete(userDtoId);
    }
}
