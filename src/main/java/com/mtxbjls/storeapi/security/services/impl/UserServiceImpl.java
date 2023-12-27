package com.mtxbjls.storeapi.security.services.impl;

import com.mtxbjls.storeapi.exceptions.UniqueConstraintViolationException;
import com.mtxbjls.storeapi.security.dtos.RequestUserDTO;
import com.mtxbjls.storeapi.security.dtos.ResponseUserDTO;
import com.mtxbjls.storeapi.security.mappers.UserMapper;
import com.mtxbjls.storeapi.security.models.Role;
import com.mtxbjls.storeapi.security.models.User;
import com.mtxbjls.storeapi.security.repositories.RoleRepository;
import com.mtxbjls.storeapi.security.repositories.UserRepository;
import com.mtxbjls.storeapi.security.services.IUserService;
import com.mtxbjls.storeapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public ResponseUserDTO registerUser(RequestUserDTO requestUserDTO) {
        if (userRepository.existsByUsername(requestUserDTO.getUsername())) {
            throw new UniqueConstraintViolationException("Error: Username is already taken!");
        }
        if(userRepository.existsByEmail(requestUserDTO.getEmail())) {
            throw new UniqueConstraintViolationException("Error: Email is already in use!");
        }
        return registerUser(requestUserDTO, Constants.Roles.CUSTOMER);
    }

    @Transactional
    public ResponseUserDTO registerUser(RequestUserDTO requestUserDTO, String role) {
        if (userRepository.existsByUsername(requestUserDTO.getUsername())) {
            throw new UniqueConstraintViolationException("Error: Username is already taken!");
        }
        User user = UserMapper.mapToUser(requestUserDTO);
        user.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(role));
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return UserMapper.mapToResponseUserDTO(savedUser);
    }
}
