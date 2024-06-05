package com.example.filtro_meta.api.dto.request;

import com.example.filtro_meta.utils.enums.State;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {
    @NotBlank(message = "User name is required.")
    @Size(max = 100, message = "User name can't be longer than 100 characters.")
    private String name;

    @Email(message = "The email must be a valid email [example@example.com]")
    @Size( max = 100, message = "Email can't be longer than 100 characters.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters long.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*])(?=\\S+$).{8,20}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace."
    )
    private String password;

    @NotNull(message = "The status of the user is required.")
    private State active;
}