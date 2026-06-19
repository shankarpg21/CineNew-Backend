package com.example.Cinenew_backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDTO {

    @NotBlank(message="Email cannot be blank")
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
    
}