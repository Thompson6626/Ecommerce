package com.ecommerce.ecommerce.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email is mandatory ")
    @NotBlank(message = "Email is mandatory ")
    private String email;
    @NotEmpty(message = "Password is mandatory ")
    @NotBlank(message = "Password is mandatory ")
    private String password;




}
