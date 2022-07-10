package com.example.ShareIt.user;

import com.example.ShareIt.user.dto.UserDto;
import com.example.ShareIt.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@Validated
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto UserDto) {
        return userService.create(UserDto);
    }

    @PatchMapping(value = "/{userId}")
    public UserDto update(@PathVariable int userId,
                          @RequestBody UserDto UserDto) {
        return userService.update(userId, UserDto);
    }

    @GetMapping(value = "/{userId}")
    public UserDto getUser(@PathVariable int userId) {
        return userService.getUser(userId);
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
