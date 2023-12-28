package com.mtxbjls.storeapi.security.services;

import com.mtxbjls.storeapi.security.dtos.*;

public interface IUserService {
    ResponseUserDTO registerUser(RequestUserDTO requestUserDTO);
    ResponseUserDTO registerUser(RequestUserDTO requestUserDTO, String role);
    ResponseLoginDTO loginUser(RequestLoginDTO requestLoginDTO);

    ResponseUserDTO getProfile();
}
