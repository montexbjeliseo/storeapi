package com.mtxbjls.storeapi.security.services;

import com.mtxbjls.storeapi.security.dtos.RequestLoginDTO;
import com.mtxbjls.storeapi.security.dtos.RequestUserDTO;
import com.mtxbjls.storeapi.security.dtos.ResponseLoginDTO;
import com.mtxbjls.storeapi.security.dtos.ResponseUserDTO;

public interface IUserService {
    ResponseUserDTO registerUser(RequestUserDTO requestUserDTO);
    ResponseUserDTO registerUser(RequestUserDTO requestUserDTO, String role);
    ResponseLoginDTO loginUser(RequestLoginDTO requestLoginDTO);
}
