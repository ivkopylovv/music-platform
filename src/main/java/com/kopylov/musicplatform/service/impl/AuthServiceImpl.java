package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.UserDAO;
import com.kopylov.musicplatform.dto.request.UserCredsDTO;
import com.kopylov.musicplatform.entity.Role;
import com.kopylov.musicplatform.entity.User;
import com.kopylov.musicplatform.enums.RoleName;
import com.kopylov.musicplatform.exception.AlreadyExistsException;
import com.kopylov.musicplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

import static com.kopylov.musicplatform.constants.ErrorMessage.USER_ALREADY_EXISTS;
import static com.kopylov.musicplatform.constants.ErrorMessage.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, UserService {
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
        });

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public void registerUser(UserCredsDTO userCreds) {
        System.out.println(userCreds.getPassword());
        System.out.println(userCreds.getUsername());
        String username = userCreds.getUsername();
        userDAO.findByUsername(username)
                .ifPresent((user) -> { throw new AlreadyExistsException(USER_ALREADY_EXISTS); });
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(userCreds.getPassword()));
        Role role = new Role(1L, RoleName.ROLE_USER);
        user.getRoles().add(role);
        userDAO.save(user);
    }
}
