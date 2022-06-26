package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.dto.request.UserCredsDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthService {
    void registerUser(UserCredsDTO userCreds);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
