package com.example.ShareIt.user;

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
    public User create(@RequestBody @Valid User user) {
        return userService.create(user);
    }
    @PutMapping
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping(value = "/{userId}")
    public User getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }
    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping(value = "/{userId}")
    public void delete(@PathVariable int userId) {
        userService.delete(userId);
    }

}
