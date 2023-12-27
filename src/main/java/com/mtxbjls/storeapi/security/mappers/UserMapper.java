package com.mtxbjls.storeapi.security.mappers;

import com.mtxbjls.storeapi.security.dtos.RequestUserDTO;
import com.mtxbjls.storeapi.security.dtos.ResponseUserDTO;
import com.mtxbjls.storeapi.security.models.Role;
import com.mtxbjls.storeapi.security.models.User;

public class UserMapper {
    public static ResponseUserDTO mapToResponseUserDTO(User user) {
        return ResponseUserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
                .build();
    }

    public static User mapToUser(RequestUserDTO requestUserDTO) {
        return User.builder()
                .username(requestUserDTO.getUsername())
                .password("")
                .firstName(requestUserDTO.getFirstName())
                .lastName(requestUserDTO.getLastName())
                .email(requestUserDTO.getEmail())
                .build();
    }
}
