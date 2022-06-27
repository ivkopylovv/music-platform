package com.kopylov.musicplatform.service;

import com.kopylov.musicplatform.dto.request.SaveUserDTO;
import com.kopylov.musicplatform.dto.request.UpdateUserDTO;
import com.kopylov.musicplatform.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User getUser(String username);

    List<User> getUsers();

    List<User> getSortedUsers(boolean asc, String attribute);

    void kickUser(String username);

    void updateUserInfo(String username, UpdateUserDTO dto) throws IOException;

    void saveUser(SaveUserDTO dto) throws IOException;

    List<User> findUsersByFirstNameLastName(String firstName, String lastName);
}
