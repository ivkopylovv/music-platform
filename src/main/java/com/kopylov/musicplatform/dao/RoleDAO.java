package com.kopylov.musicplatform.dao;

import com.kopylov.musicplatform.entity.Role;
import com.kopylov.musicplatform.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
