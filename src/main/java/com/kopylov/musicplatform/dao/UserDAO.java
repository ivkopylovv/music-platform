package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    void deleteUserByUsername(String username);

    List<User> findByFirstNameOrLastName(String firstName, String lastName);
}
