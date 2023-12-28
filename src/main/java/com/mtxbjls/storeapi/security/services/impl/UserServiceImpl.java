package com.mtxbjls.storeapi.security.services.impl;

import com.mtxbjls.storeapi.exceptions.UniqueConstraintViolationException;
import com.mtxbjls.storeapi.security.dtos.*;
import com.mtxbjls.storeapi.security.mappers.UserMapper;
import com.mtxbjls.storeapi.security.models.Role;
import com.mtxbjls.storeapi.security.models.User;
import com.mtxbjls.storeapi.security.repositories.RoleRepository;
import com.mtxbjls.storeapi.security.repositories.UserRepository;
import com.mtxbjls.storeapi.security.services.IUserService;
import com.mtxbjls.storeapi.security.utils.JwtTokenUtil;
import com.mtxbjls.storeapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public ResponseUserDTO registerUser(RequestUserDTO requestUserDTO) {
        if (userRepository.existsByEmail(requestUserDTO.getEmail())) {
            throw new UniqueConstraintViolationException("Error: Email is already in use!");
        }
        return registerUser(requestUserDTO, Constants.Roles.CUSTOMER);
    }

    @Transactional
    public ResponseUserDTO registerUser(RequestUserDTO requestUserDTO, String role) {
        if (userRepository.existsByEmail(requestUserDTO.getEmail())) {
            throw new UniqueConstraintViolationException("Error: Email is already in use!");
        }
        User user = UserMapper.mapToUser(requestUserDTO);
        user.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(role));
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return UserMapper.mapToResponseUserDTO(savedUser);
    }

    @Override
    public ResponseLoginDTO loginUser(RequestLoginDTO requestLoginDTO) {
        if (!userRepository.existsByEmail(requestLoginDTO.getEmail())) {
            throw new UsernameNotFoundException("User not found");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    requestLoginDTO.getEmail(), requestLoginDTO.getPassword()));
            
            return new ResponseLoginDTO(jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal()));
        } catch (BadCredentialsException ex) {
            throw ex;
        }
    }

    @Override
    public ResponseUserDTO getProfile() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserMapper.mapToResponseUserDTO((User) userDetails);
    }
}
