package ru.kobaclothes.eshop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.kobaclothes.eshop.validation.email.ValidEmail;
import ru.kobaclothes.eshop.validation.password.PasswordMatches;

@Data
@PasswordMatches
public class UserDTO {

    @ValidEmail
    @NotNull
    @NotEmpty
        private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
    private String matchingPassword;
}
