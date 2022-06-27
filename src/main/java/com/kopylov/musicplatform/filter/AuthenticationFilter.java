package com.kopylov.musicplatform.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopylov.musicplatform.exception.data.ApiError;
import com.kopylov.musicplatform.helper.DateHelper;
import com.kopylov.musicplatform.helper.TokenHelper;
import com.kopylov.musicplatform.mapper.response.ResponseTokensMapper;
import com.kopylov.musicplatform.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.kopylov.musicplatform.constants.ErrorMessage.LOGIN_OR_PASSWORD_ARE_INCORRECT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final TokenHelper tokenHelper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getParameter("username"),
                        request.getParameter("password")
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        String access_token = tokenHelper.getAccessToken(user, request);
        String refresh_token = tokenHelper.getRefreshToken(user, request);

        tokenService.saveToken(user.getUsername(), refresh_token);
        Map<String, String> tokens = ResponseTokensMapper.getTokensMap(access_token, refresh_token);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        SecurityContextHolder.clearContext();

        ApiError apiError = new ApiError(
                DateHelper.getCurrentDate(),
                FORBIDDEN.value(),
                FORBIDDEN,
                LOGIN_OR_PASSWORD_ARE_INCORRECT,
                request.getServletPath()
        );

        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), apiError);
    }


}