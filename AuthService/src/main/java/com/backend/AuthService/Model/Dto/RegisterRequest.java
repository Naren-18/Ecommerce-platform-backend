package com.backend.AuthService.Model.Dto;

import com.backend.AuthService.Model.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "The password cannot be empty")
    @Size(min = 4 , max = 8, message = "The password length should be minimum 4 characters long and max 8 characters")
    private String password;

    @NotNull(message = "Role cannot be empty")
    private Role role;
}
