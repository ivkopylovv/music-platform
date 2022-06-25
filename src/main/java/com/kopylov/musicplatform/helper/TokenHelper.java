package com.kopylov.musicplatform.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kopylov.musicplatform.entity.Role;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@UtilityClass
public class TokenHelper {
    private final String SECRET_KEY = "secret";
    private final String CLAIMS_NAME = "roles";

    public Algorithm getToken() {
        return Algorithm.HMAC256((SECRET_KEY).getBytes());
    }

    public String getAccessToken(User user, HttpServletRequest request) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateHelper.getAccessTokenTimeAlive())
                .withIssuer(request.getRequestURL().toString())
                .withClaim(CLAIMS_NAME, user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(getToken());
    }

    public String getAccessToken(com.kopylov.musicplatform.entity.User user, HttpServletRequest request) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateHelper.getAccessTokenTimeAlive())
                .withIssuer(request.getRequestURL().toString())
                .withClaim(CLAIMS_NAME, user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .map(roleName -> roleName.toString())
                        .collect(Collectors.toList()))
                .sign(getToken());
    }

    public String getRefreshToken(User user, HttpServletRequest request) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateHelper.getRefreshTokenTimeAlive())
                .withIssuer(request.getRequestURL().toString())
                .sign(getToken());
    }

    public String getRefreshToken(com.kopylov.musicplatform.entity.User user, HttpServletRequest request) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(DateHelper.getRefreshTokenTimeAlive())
                .withIssuer(request.getRequestURL().toString())
                .sign(getToken());
    }

    public String getTokenByAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    public String getUserLoginByToken(String token) {
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
