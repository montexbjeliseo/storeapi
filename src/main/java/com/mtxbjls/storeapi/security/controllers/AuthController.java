package com.mtxbjls.storeapi.security.controllers;

import com.mtxbjls.storeapi.security.dtos.*;
import com.mtxbjls.storeapi.security.services.IUserService;
import com.mtxbjls.storeapi.utils.Constants.Endpoints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.AUTH)
@Slf4j
public class AuthController {

    private final IUserService userService;

    @PostMapping(Endpoints.REGISTER)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserDTO createUser(@RequestBody RequestUserDTO requestUserDTO) {
        try {
            log.info("Creating user {}", requestUserDTO);
            return userService.registerUser(requestUserDTO);
        } catch (IllegalArgumentException | IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    @PostMapping(Endpoints.LOGIN)
    @ResponseStatus(HttpStatus.OK)
    public ResponseLoginDTO loginUser(@RequestBody RequestLoginDTO requestLoginDTO){
        return userService.loginUser(requestLoginDTO);
    }

    @GetMapping(Endpoints.PROFILE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserDTO getProfile(){
        return userService.getProfile();
    }
}
