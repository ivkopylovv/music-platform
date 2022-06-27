package com.kopylov.musicplatform.service.impl;

import com.kopylov.musicplatform.dao.RoleDAO;
import com.kopylov.musicplatform.dao.TokenDAO;
import com.kopylov.musicplatform.dao.UserDAO;
import com.kopylov.musicplatform.dto.request.RoleToUserDTO;
import com.kopylov.musicplatform.dto.request.SaveUserDTO;
import com.kopylov.musicplatform.dto.request.UpdateUserDTO;
import com.kopylov.musicplatform.entity.Role;
import com.kopylov.musicplatform.entity.User;
import com.kopylov.musicplatform.enums.RoleName;
import com.kopylov.musicplatform.exception.ResourceNotFoundException;
import com.kopylov.musicplatform.helper.FileHelper;
import com.kopylov.musicplatform.helper.SortHelper;
import com.kopylov.musicplatform.mapper.response.ResponseUserMapper;
import com.kopylov.musicplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.kopylov.musicplatform.constants.ErrorMessage.ROLE_NOT_FOUND;
import static com.kopylov.musicplatform.constants.ErrorMessage.USER_NOT_FOUND;
import static com.kopylov.musicplatform.constants.FilePath.DB_IMAGE_PATH;
import static com.kopylov.musicplatform.constants.FilePath.STATIC_IMAGE_PATH;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final TokenDAO tokenDAO;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleDAO roleDAO;

    @Override
    public User getUser(String username) {
        return userDAO.findUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public List<User> getSortedUsers(boolean asc, String attribute) {
        return userDAO.findAll(SortHelper.getSortScript(asc, attribute));
    }

    @Override
    public void kickUser(String username) {
        tokenDAO.deleteByUserUsername(username);
        userDAO.deleteUserByUsername(username);
    }

    @Override
    public void updateUserInfo(String username, UpdateUserDTO dto) throws IOException {
        User user = getUser(username);

        FileHelper.deleteFile(user.getImageName(), STATIC_IMAGE_PATH);
        MultipartFile file = dto.getImage();
        FileHelper.saveUploadedFile(file, STATIC_IMAGE_PATH);
        String imageName = DB_IMAGE_PATH + file.getOriginalFilename();
        String password = dto.getPassword();

        dto.setPassword(passwordEncoder.encode(password));
        User updatedUser = ResponseUserMapper.updateDTOToEntity(dto, imageName);
        updatedUser.setUsername(username);
        updatedUser.setId(user.getId());

        userDAO.save(updatedUser);
    }

    @Override
    public void saveUser(SaveUserDTO dto) throws IOException {
        MultipartFile file = dto.getImage();
        FileHelper.saveUploadedFile(file, STATIC_IMAGE_PATH);
        String imageName = DB_IMAGE_PATH + file.getOriginalFilename();

        String password = dto.getPassword();
        dto.setPassword(passwordEncoder.encode(password));
        List<Role> roles = dto.getRoles().stream()
                .map(role -> roleDAO.findByName(RoleName.valueOf(role))
                        .orElseThrow(() -> new ResourceNotFoundException(ROLE_NOT_FOUND)))
                .collect(Collectors.toList());
        User user = ResponseUserMapper.saveDTOToEntity(dto, roles, imageName);

        userDAO.save(user);
    }

    @Override
    public void addRoleToUser(RoleToUserDTO dto) {
        Role role = roleDAO.findByName(RoleName.valueOf(dto.getRoleName()))
                .orElseThrow(() -> new ResourceNotFoundException(ROLE_NOT_FOUND));
        User user = userDAO.findUserByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        user.getRoles().add(role);
    }

    @Override
    public List<User> findUsersByFirstNameLastName(String firstName, String lastName) {
        return userDAO.findByFirstNameOrLastName(firstName, lastName);
    }
}
