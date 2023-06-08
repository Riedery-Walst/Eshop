package ru.kobaclothes.eshop.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.kobaclothes.eshop.validation.password.PasswordMatches;

@Data
@PasswordMatches
public class PasswordChangeRequest {
    private String currentPassword;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String newPassword;
    private String matchingPassword;
}