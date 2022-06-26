package com.kopylov.musicplatform.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopylov.musicplatform.dao.AuthDAO;
import com.kopylov.musicplatform.dao.TokenDAO;
import com.kopylov.musicplatform.dto.request.UserCredsDTO;
import com.kopylov.musicplatform.entity.Role;
import com.kopylov.musicplatform.entity.User;
import com.kopylov.musicplatform.enums.RoleName;
import com.kopylov.musicplatform.exception.AlreadyExistsException;
import com.kopylov.musicplatform.exception.NotFoundException;
import com.kopylov.musicplatform.exception.UnauthorizedException;
import com.kopylov.musicplatform.helper.DateHelper;
import com.kopylov.musicplatform.helper.TokenHelper;
import com.kopylov.musicplatform.mapper.response.ResponseTokensMapper;
import com.kopylov.musicplatform.service.AuthService;
import com.kopylov.musicplatform.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static com.kopylov.musicplatform.constants.ErrorMessage.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {
    private final AuthDAO authDAO;
    private final TokenDAO tokenDAO;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authDAO.findUserByUsername(username)
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
        String username = userCreds.getUsername();
        authDAO.findUserByUsername(username)
                .ifPresent((user) -> {
                    throw new AlreadyExistsException(USER_ALREADY_EXISTS);
                });

        User user = new User()
                .setUsername(username)
                .setPassword(passwordEncoder.encode(userCreds.getPassword()));
        Role role = new Role(1L, RoleName.ROLE_USER);
        user.getRoles().add(role);

        authDAO.save(user);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (TokenHelper.isAuthorizationHeaderValid(authorizationHeader)) {
            successfulRefresh(request, response, authorizationHeader);
        } else {
            throw new UnauthorizedException(USER_IS_UNAUTHORIZED);
        }
    }

    @Override
    public void logout(String authorizationHeader) {
        String token = TokenHelper.getTokenByAuthorizationHeader(authorizationHeader);
        String username = TokenHelper.getUsernameByToken(token);
        tokenDAO.deleteByUserUsername(username);
    }

    private void successfulRefresh(HttpServletRequest request, HttpServletResponse response, String authorizationHeader) throws IOException {
        String refresh_token = TokenHelper.getTokenByAuthorizationHeader(authorizationHeader);
        String username = TokenHelper.getUsernameByToken(refresh_token);

        User user = authDAO.findUserByUsername(username)
                .orElseThrow(() -> new UnauthorizedException(USER_IS_UNAUTHORIZED));
        Date expiredInDate = tokenDAO.findByToken(refresh_token)
                .orElseThrow(() -> new UnauthorizedException(USER_IS_UNAUTHORIZED))
                .getExpirationDate();

        if (expiredInDate.before(DateHelper.getCurrentDate())) {
            throw new UnauthorizedException(USER_IS_UNAUTHORIZED);
        }

        String access_token = TokenHelper.getAccessToken(user, request);
        String new_refresh_token = TokenHelper.getRefreshToken(user, request);
        tokenService.saveToken(username, new_refresh_token);

        Map<String, String> tokens = ResponseTokensMapper.getTokensMap(access_token, new_refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

}
