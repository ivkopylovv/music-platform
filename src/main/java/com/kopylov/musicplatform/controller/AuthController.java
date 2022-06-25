package com.kopylov.musicplatform.controller;

import com.kopylov.musicplatform.dto.request.UserCredsDTO;
import com.kopylov.musicplatform.dto.response.SuccessMessageDTO;
import com.kopylov.musicplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping(value = "/reg")
    ResponseEntity<SuccessMessageDTO> registerUser(@RequestBody UserCredsDTO userCreds) {
        userService.registerUser(userCreds);
        return ResponseEntity.ok().body(new SuccessMessageDTO("Nice"));
    }
}
