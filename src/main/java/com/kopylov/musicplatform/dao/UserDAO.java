package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
