package com.example.ShareIt.user;

import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto userDto) {
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
