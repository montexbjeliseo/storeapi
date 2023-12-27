package com.mtxbjls.storeapi.security.controllers;

import com.mtxbjls.storeapi.security.dtos.RequestLoginDTO;
import com.mtxbjls.storeapi.security.dtos.RequestUserDTO;
import com.mtxbjls.storeapi.security.dtos.ResponseLoginDTO;
import com.mtxbjls.storeapi.security.dtos.ResponseUserDTO;
import com.mtxbjls.storeapi.security.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final IUserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserDTO createUser(@RequestBody RequestUserDTO requestUserDTO) {
        try {
            log.info("Creating user {}", requestUserDTO);
            return userService.registerUser(requestUserDTO);
        } catch (IllegalArgumentException | IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLoginDTO loginUser(@RequestBody RequestLoginDTO requestLoginDTO){
        return userService.loginUser(requestLoginDTO);
    }
}
