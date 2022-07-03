package com.example.ShareIt.user;


import com.example.ShareIt.exception.EmailIsDublicated;
import com.example.ShareIt.exception.InvalidUserIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage {

    public static final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    public User create(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getEmail().isBlank()) {
            throw new InvalidUserIdException("Сannot be null");
        }
        User newUser = new User(id, user.getName(), user.getEmail());
        if (checkEmail(newUser) == true) {
            throw new EmailIsDublicated("Email already exists");
        }
        users.put(newUser.getId(), newUser);
        id++;
        log.info("field with type {} and id={} added.", newUser.getClass(), newUser.getId());
        return users.get(newUser.getId());
    }

    private boolean checkEmail(User newUser) {
        for (User x : users.values()) {
            if (x.getEmail().equals(newUser.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public User getUser(int userId) {
        if (userId < 0) {
            throw new InvalidUserIdException("The user ID cannot be negative.");
        }
        log.info("field  with id={}", userId);
        return users.get(userId);
    }

    public User update(User user) {
        for (User x : users.values()) {
            if (x.getId() == user.getId()) {
                if (user.getName() != null) {
                    x.setName(user.getName());
                }
                if (user.getEmail() != null) {
                    if (checkEmail(user) == true) {
                        throw new EmailIsDublicated("Email already exists");
                    }
                    x.setEmail(user.getEmail());
                }
            }
        }
        log.info("field  with id={} updated: {}", user.getId(), user.getClass());
        return users.get(user.getId());
    }

    public void delete(int userId) {
        users.remove(userId);
        log.info("field  with id={} deleted", userId);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }


}
