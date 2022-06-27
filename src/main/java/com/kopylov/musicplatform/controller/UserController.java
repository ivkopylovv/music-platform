package com.kopylov.musicplatform.controller;

import com.kopylov.musicplatform.dto.request.RoleToUserDTO;
import com.kopylov.musicplatform.dto.request.SaveUserDTO;
import com.kopylov.musicplatform.dto.request.UpdateUserDTO;
import com.kopylov.musicplatform.dto.response.SuccessMessageDTO;
import com.kopylov.musicplatform.dto.response.UserListDTO;
import com.kopylov.musicplatform.entity.User;
import com.kopylov.musicplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.kopylov.musicplatform.constants.SuccessMessage.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @GetMapping(value = "/users")
    public ResponseEntity<UserListDTO> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok().body(new UserListDTO(users));
    }

    @GetMapping(value = "/users", params = {"asc", "attribute"})
    public ResponseEntity<UserListDTO> getSortedUsers(
            @RequestParam("asc") boolean asc, @RequestParam("attribute") String attribute) {
        List<User> users = userService.getSortedUsers(asc, attribute);
        return ResponseEntity.ok().body(new UserListDTO(users));
    }

    @DeleteMapping(value = "/users/{username}")
    public ResponseEntity<SuccessMessageDTO> kickUser(@PathVariable("username") String username) {
        userService.kickUser(username);
        return ResponseEntity.ok().body(new SuccessMessageDTO(USER_WAS_KICKED));
    }

    @PutMapping(value = "/users/{username}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessMessageDTO> updateUserInfo(
            @PathVariable("username") String username, @ModelAttribute UpdateUserDTO dto) throws IOException {
        userService.updateUserInfo(username, dto);
        return ResponseEntity.ok().body(new SuccessMessageDTO(USER_INFORMATION_WAS_UPDATED));
    }

    @PostMapping(value = "/users", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessMessageDTO> saveUser(@ModelAttribute SaveUserDTO dto) throws IOException {
        userService.saveUser(dto);
        return ResponseEntity.ok().body(new SuccessMessageDTO(USER_WAS_SAVED));
    }

    @PostMapping(value = "/users/add-role")
    public ResponseEntity<SuccessMessageDTO> addRoleToUser(@RequestBody RoleToUserDTO dto) {
        userService.addRoleToUser(dto);
        return ResponseEntity.ok().body(new SuccessMessageDTO(ROLE_WAS_ADDED));
    }

    @GetMapping(value = "/users/find", params = {"firstName", "lastName"})
    public ResponseEntity<UserListDTO> findUsers(
            @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        List<User> users = userService.findUsersByFirstNameLastName(firstName, lastName);
        return ResponseEntity.ok().body(new UserListDTO(users));
    }
}
