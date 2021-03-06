package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenDAO extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    Optional<Token> findByUserUsername(String username);

    void deleteByUserUsername(String username);
}
