package com.example.ShareIt.user;


import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserService {

    private final InMemoryUserStorage inMemoryUserStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User create(User user) {
        return inMemoryUserStorage.create(user);
    }

    public User update(User user) {
        return inMemoryUserStorage.update(user);
    }

    public User getUser(int userId) {
        return inMemoryUserStorage.getUser(userId);
    }

    public Collection<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    public void delete(int userId) {
        inMemoryUserStorage.delete(userId);
    }
}
