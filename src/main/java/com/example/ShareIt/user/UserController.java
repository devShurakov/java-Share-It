package com.example.ShareIt.user;

import com.example.ShareIt.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@Validated
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto UserDto) {
        return userServiceImpl.create(UserDto);
    }

    @PatchMapping(value = "/{userId}")
    public UserDto update(@PathVariable int userId,
                          @RequestBody UserDto UserDto) {
        return userServiceImpl.update(userId, UserDto);
    }

    @GetMapping(value = "/{userId}")
    public UserDto getUser(@PathVariable int userId) {
        return userServiceImpl.getUser(userId);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @DeleteMapping(value = "/{userId}")
    public void delete(@PathVariable int userId) {
        userServiceImpl.delete(userId);
    }

}
