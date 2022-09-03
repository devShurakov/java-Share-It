package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.IncorrectEmailexception;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;



@Controller
@Validated
@RequestMapping(value = "/users")
public class UserController {

    private final UserClient userClient;

    @Autowired
    public UserController(UserClient userClient) {

        this.userClient = userClient;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UserDto userDto)  {
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new IncorrectEmailexception("user.Email сan't be null or blank");
        }
        checkEmail(userDto.getEmail());
        return userClient.create(userDto);
    }

    private void checkEmail(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            throw new IncorrectEmailexception("user.Email should be in email format.");
        }
    }

    @PatchMapping(value = "/{userId}")
    public ResponseEntity<Object>  update(@PathVariable int userId,
                          @RequestBody UserDto userDto) {
        if (userDto.getEmail() != null) {
            checkEmail(userDto.getEmail());
        }
        return userClient.update((long) userId, userDto);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Object>  getUser(@PathVariable int userId) {

        return userClient.getDtoUser(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {

        return userClient.getAllUsers();
    }


    @DeleteMapping(value = "/{userId}")
    public Object delete(@PathVariable int userId) {

        return userClient.delete(userId);
    }

}
