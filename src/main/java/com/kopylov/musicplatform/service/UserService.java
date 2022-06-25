package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.dto.request.UserCredsDTO;

public interface UserService {
    void registerUser(UserCredsDTO userCreds);
}
