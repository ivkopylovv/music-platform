package com.kopylov.musicplatform.config;

import com.kopylov.musicplatform.filter.AuthenticationFilter;
import com.kopylov.musicplatform.filter.AuthorizationFilter;
import com.kopylov.musicplatform.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.kopylov.musicplatform.constants.AuthAntPatterns.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean(), tokenService);
        authenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");

        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN, REGISTRATION, TOKEN_REFRESH).permitAll()
                .antMatchers(POST, SONGS, ALBUMS, ARTISTS).hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(DELETE, SONGS, ALBUMS, ARTISTS).hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(PUT, SONGS, ALBUMS, ARTISTS).hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/**").hasAnyAuthority("ROLE_USER")
                .and()
                .addFilter(authenticationFilter)
                .addFilterBefore(new AuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
