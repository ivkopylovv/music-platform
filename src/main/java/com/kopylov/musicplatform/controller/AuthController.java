package com.kopylov.musicplatform.controller;

import com.kopylov.musicplatform.dto.request.UserCredsDTO;
import com.kopylov.musicplatform.dto.response.SuccessMessageDTO;
import com.kopylov.musicplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.kopylov.musicplatform.constants.SuccessMessage.USER_HAS_BEEN_REGISTERED;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "auth/reg")
    ResponseEntity<SuccessMessageDTO> registerUser(@RequestBody UserCredsDTO userCreds) {
        authService.registerUser(userCreds);
        return ResponseEntity.ok().body(new SuccessMessageDTO(USER_HAS_BEEN_REGISTERED));
    }

    @GetMapping(value = "auth/token/refresh")
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }
}
