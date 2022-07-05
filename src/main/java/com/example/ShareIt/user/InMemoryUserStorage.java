package com.example.ShareIt.user;

import com.example.ShareIt.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage {
    public static final Map<Integer, User> users = new HashMap<>();

    public User create(User user) {
        users.put(user.getId(), user);
        log.info("field with type {} and id={} added.", user.getClass(), user.getId());
        return users.get(user.getId());
    }
    public User getUser(int userId) {
        log.info("field  with id={}", userId);
        return users.get(userId);
    }
    public User update(int userId, User user) {
        for (User x : users.values()) {
            if (x.getId() == userId) {
                if (user.getName() != null) {
                    x.setName(user.getName());
                }
                if (user.getEmail() != null) {
                    x.setEmail(user.getEmail());
                }
            }
        }
        log.info("field  with id={} updated: {}", user.getId(), user.getClass());
        return users.get(userId);
    }
    public void delete(int userDtoId) {
        users.remove(userDtoId);
        log.info("field  with id={} deleted", userDtoId);
    }
    public Collection<User> getAllUsers() {
        log.info("All users received");
        return users.values();
    }


}
