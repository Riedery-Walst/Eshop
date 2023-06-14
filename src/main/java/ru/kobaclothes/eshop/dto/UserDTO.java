package ru.kobaclothes.eshop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.kobaclothes.eshop.validation.email.ValidEmail;
import ru.kobaclothes.eshop.validation.password.PasswordMatches;
import ru.kobaclothes.eshop.validation.password.ValidPassword;

@Data
@PasswordMatches
public class UserDTO {

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @ValidPassword
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String newPassword;
    private String currentPassword;
    private String matchingPassword;

}
