package com.kopylov.musicplatform.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private MultipartFile image;
    private List<String> roles;
}
