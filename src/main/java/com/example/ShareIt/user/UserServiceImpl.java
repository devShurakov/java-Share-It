package com.example.ShareIt.user;

import com.example.ShareIt.exception.EmailIsDublicated;
import com.example.ShareIt.exception.InvalidUserIdException;
import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    private int id = 1;
    private final InMemoryUserStorage inMemoryUserStorage;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(InMemoryUserStorage inMemoryUserStorage, UserMapper userMapper) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userMapper = userMapper;
    }
    public UserDto create(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty() || userDto.getEmail().isBlank()) {
            throw new InvalidUserIdException("Сannot be null");
        }
        if (checkEmail(userDto.getEmail()) == true) {
            throw new EmailIsDublicated("Email already exists");
        }
        User newUser = makeUser(userMapper.mapToUser(userDto));
        User user = inMemoryUserStorage.create(newUser);
        return userMapper.mapToUserDto(user);
    }
    public User makeUser(User user) {
        User newUser = new User(id, user.getName(), user.getEmail());
        id++;
        return newUser;
    }
    private boolean checkEmail(String email) {
        for (User x : inMemoryUserStorage.users.values()) {
            if (x.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public UserDto update(int userId, UserDto userDto) {
        if(userDto.getEmail() != null) {
            if (checkEmail(userDto.getEmail()) == true) {
                throw new EmailIsDublicated("Email already exists");
            }
        }
        User user = inMemoryUserStorage.update(userId, userMapper.mapToUser(userDto));
        return userMapper.mapToUserDto(user);
    }
    public UserDto getUser(int userId) {
        if (userId < 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        User user = inMemoryUserStorage.getUser(userId);
        return userMapper.mapToUserDto(user);
    }
    public Collection<UserDto> getAllUsers() {
        Collection<User> userItems = inMemoryUserStorage.getAllUsers();
        return userMapper.maptoAllUserDto(userItems);
    }
    public void delete(int userDtoId) {
        inMemoryUserStorage.delete(userDtoId);
    }
}
