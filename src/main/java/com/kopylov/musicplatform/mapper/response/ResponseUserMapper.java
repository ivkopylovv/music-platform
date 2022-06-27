package com.kopylov.musicplatform.mapper.response;

import com.kopylov.musicplatform.dto.request.SaveUserDTO;
import com.kopylov.musicplatform.dto.request.UpdateUserDTO;
import com.kopylov.musicplatform.entity.Role;
import com.kopylov.musicplatform.entity.User;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;

@UtilityClass
public class ResponseUserMapper {

    public User updateDTOToEntity(UpdateUserDTO dto, String imageName) {
        return new User()
                .setPassword(dto.getPassword())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setImageName(imageName);
    }

    public User saveDTOToEntity(SaveUserDTO dto, List<Role> roles, String imageName) {
        return new User()
                .setUsername(dto.getUsername())
                .setPassword(dto.getPassword())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setImageName(imageName)
                .setRoles(new HashSet<>(roles));
    }
}
