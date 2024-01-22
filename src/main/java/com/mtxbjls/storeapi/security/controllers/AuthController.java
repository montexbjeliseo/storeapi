package com.mtxbjls.storeapi.security.controllers;

import com.mtxbjls.storeapi.security.dtos.*;
import com.mtxbjls.storeapi.security.services.IUserService;
import com.mtxbjls.storeapi.utils.Constants;
import com.mtxbjls.storeapi.utils.Constants.Endpoints;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = Constants.Docs.Tags.AUTH, description = Constants.Docs.Tags.AUTH_DESCRIPTION)
@RestController
@RequiredArgsConstructor
@RequestMapping(value = Endpoints.AUTH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final IUserService userService;

    @Operation(summary = Constants.Docs.Operations.AUTH_CREATE_USER)
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.CREATED,
                description = Constants.Docs.ResponseDescriptions.USER_CREATED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.BAD_REQUEST,
                description = Constants.Docs.ResponseDescriptions.BAD_REQUEST
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.CONFLICT,
                description = Constants.Docs.ResponseDescriptions.CONFLICT
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.INTERNAL_SERVER_ERROR,
                description = Constants.Docs.ResponseDescriptions.INTERNAL_SERVER_ERROR
        )
    })
    @PostMapping(Endpoints.REGISTER)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserDTO createUser(@RequestBody RequestUserDTO requestUserDTO) {
        try {
            return userService.registerUser(requestUserDTO);
        } catch (IllegalArgumentException | IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = Constants.Docs.Operations.AUTH_LOGIN)
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.USER_LOGGED_IN
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.BAD_REQUEST,
                description = Constants.Docs.ResponseDescriptions.BAD_REQUEST
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.NOT_FOUND,
                description = Constants.Docs.ResponseDescriptions.NOT_FOUND_USER
        ),
    })
    @PostMapping(Endpoints.LOGIN)
    @ResponseStatus(HttpStatus.OK)
    public ResponseLoginDTO loginUser(@RequestBody RequestLoginDTO requestLoginDTO){
        return userService.loginUser(requestLoginDTO);
    }

    @Operation(summary = Constants.Docs.Operations.AUTH_GET_PROFILE)
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.USER_PROFILE
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.UNAUTHORIZED,
                description = Constants.Docs.ResponseDescriptions.UNAUTHORIZED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.INTERNAL_SERVER_ERROR,
                description = Constants.Docs.ResponseDescriptions.INTERNAL_SERVER_ERROR
        ),
    })
    @GetMapping(Endpoints.PROFILE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserDTO getProfile(){
        return userService.getProfile();
    }
}
