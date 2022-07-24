package com.example.ShareIt.user.storage;

import com.example.ShareIt.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
