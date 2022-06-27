package com.kopylov.musicplatform.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kopylov.musicplatform.entity.Role;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
@Getter
public class TokenHelper {
    @Value("${token.claims}")
    private String CLAIMS;
    @Value("${token.secret.key}")
    private String SECRET_KEY;
    @Value("${token.access.time.alive}")
    private Long ACCESS_TOKEN_TIME_ALIVE;
    @Value("${token.refresh.time.alive}")
    private Long REFRESH_TOKEN_TIME_ALIVE;

    public Algorithm getToken() {
        return Algorithm.HMAC256((SECRET_KEY).getBytes());
    }

    public String getAccessToken(User user, HttpServletRequest request) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateHelper.getTokenTimeAlive(ACCESS_TOKEN_TIME_ALIVE))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(CLAIMS, user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(getToken());
    }

    public String getAccessToken(com.kopylov.musicplatform.entity.User user, HttpServletRequest request) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateHelper.getTokenTimeAlive(REFRESH_TOKEN_TIME_ALIVE))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(CLAIMS, user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .map(roleName -> roleName.toString())
                        .collect(Collectors.toList()))
                .sign(getToken());
    }

    public String getRefreshToken(User user, HttpServletRequest request) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateHelper.getTokenTimeAlive(REFRESH_TOKEN_TIME_ALIVE))
                .withIssuer(request.getRequestURL().toString())
                .sign(getToken());
    }

    public String getRefreshToken(com.kopylov.musicplatform.entity.User user, HttpServletRequest request) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateHelper.getTokenTimeAlive(ACCESS_TOKEN_TIME_ALIVE))
                .withIssuer(request.getRequestURL().toString())
                .sign(getToken());
    }

    public String getTokenByAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    public String getUsernameByToken(String token) {
        Algorithm algorithm = getToken();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();

        return username;
    }

    public boolean isAuthorizationHeaderValid(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }

}
