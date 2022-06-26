package com.kopylov.musicplatform.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopylov.musicplatform.exception.data.ApiError;
import com.kopylov.musicplatform.helper.DateHelper;
import com.kopylov.musicplatform.helper.TokenHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.kopylov.musicplatform.constants.ErrorMessage.TOKEN_NOT_VALID;
import static com.kopylov.musicplatform.constants.TokenOption.CLAIMS;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();

        if (isPathSkipped(path)) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);

            if (TokenHelper.isAuthorizationHeaderValid(authorizationHeader)) {
                try {
                    DecodedJWT decodedJWT = getDecodedJWT(authorizationHeader);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim(CLAIMS).asArray(String.class);

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    ApiError apiError = new ApiError(
                            DateHelper.getCurrentDate(),
                            FORBIDDEN.value(),
                            FORBIDDEN,
                            TOKEN_NOT_VALID,
                            request.getServletPath()
                    );

                    response.setStatus(FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), apiError);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    private DecodedJWT getDecodedJWT(String authorizationHeader) {
        String token = TokenHelper.getTokenByAuthorizationHeader(authorizationHeader);
        Algorithm algorithm = TokenHelper.getToken();
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

    private boolean isPathSkipped(String path) {
        return path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/reg") || path.equals("/api/v1/auth/token/refresh");
    }
}
