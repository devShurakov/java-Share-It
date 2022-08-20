package com.example.ShareIt.user.service.impl;

import com.example.ShareIt.user.exception.UserNotFoundException;
import com.example.ShareIt.user.mapper.UserMapper;
import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.model.User;
import com.example.ShareIt.user.storage.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class UserServiceImpl implements com.example.ShareIt.user.service.UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {

        this.userRepository = userRepository;

        this.userMapper = userMapper;
    }

    public UserDto create(UserDto userDto) {

        userRepository.findAll().stream().forEach(i -> {
            if (i.getEmail() == userDto.getEmail()) {
                throw new RuntimeException();
            }
        });
        User user = userRepository.save(userMapper.mapToUser(userDto));
        log.info("User created");
        return userMapper.mapToUserDto(user);
    }

    public UserDto update(int userId, UserDto userDto) {

        User user = getUser(userId);

        if (userDto.getName() != null) {
            log.info("Update name to {}", userDto.getName());
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            log.info("Update email to {}", userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        return userMapper.mapToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getDtoUser(long userId) {

        User user = userRepository.findById((long) userId).orElseThrow(() -> {
            log.warn("Entity not found");
            return new UserNotFoundException(String.format("Entities with id %d not found", userId));
        });
        return userMapper.mapToUserDto(user);
    }

    public User getUser(long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("Entity not found");
            return new UserNotFoundException(String.format("Entities with id %d not found", userId));
        });
        return user;
    }

    public Collection<UserDto> getAllUsers() {

        Collection<User> userItems = userRepository.findAll();
        log.info("All users");
        return userMapper.maptoAllUserDto(userItems);
    }

    public void delete(int userDtoId) {

        log.info("User was deleted");
        userRepository.deleteById((long) userDtoId);
    }

}
