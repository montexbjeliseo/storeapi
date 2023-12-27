package com.mtxbjls.storeapi.security.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseUserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String[] roles;
}
