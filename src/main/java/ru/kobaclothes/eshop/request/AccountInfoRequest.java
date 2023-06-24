package ru.kobaclothes.eshop.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.kobaclothes.eshop.model.Gender;

import java.util.Date;

@Data
public class AccountInfoRequest {
    @NotNull
    private Date birthDate;
    @NotNull
    private Gender gender;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20, message = "First Name must be between 2 and 20 characters")
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20, message = "Patronymic must be between 2 and 20 characters")
    private String patronymic;
}
