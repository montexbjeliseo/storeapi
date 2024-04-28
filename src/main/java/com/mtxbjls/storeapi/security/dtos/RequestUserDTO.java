package com.mtxbjls.storeapi.security.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestUserDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 3, max = 25)
    @Pattern(regexp = "^[a-zA-Z]*$")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 25)
    @Pattern(regexp = "^[a-zA-Z]*$")
    private String lastName;
}
