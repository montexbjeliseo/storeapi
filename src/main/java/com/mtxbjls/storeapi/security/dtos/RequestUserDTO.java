package com.mtxbjls.storeapi.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestUserDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
