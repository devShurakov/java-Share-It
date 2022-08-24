package com.example.ShareIt.user;

import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {

        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto userDto) {

        return userService.create(userDto);
    }

    @PatchMapping(value = "/{userId}")
    public UserDto update(@PathVariable int userId,
                          @RequestBody UserDto userDto) {

        return userService.update(userId, userDto);
    }

    @GetMapping(value = "/{userId}")
    public UserDto getUser(@PathVariable int userId) {

        return userService.getDtoUser(userId);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers() {

        return userService.getAllUsers();
    }

    @DeleteMapping(value = "/{userId}")
    public void delete(@PathVariable int userId) {

        userService.delete(userId);
    }

}
