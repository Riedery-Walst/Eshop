package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.dto.PasswordChangeDTO;

public interface UserService {
    void registerNewUserAccount(UserDTO userDTO);

    void initiateEmailVerification(User user);

    void initiatePasswordReset(String email);

    void setPasswordByToken(String token, PasswordChangeDTO passwordChangeDTO);

    void verifyAccountByToken(String token);

    void changePassword(String email, String newPassword, String currentPassword);
}
