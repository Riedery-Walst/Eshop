package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.model.User;

public interface UserService {
    void registerNewUserAccount(UserDTO userDTO);

    void verifyEmail(String code);

    void changePassword(String email, String newPassword, String currentPassword);

    void initiatePasswordReset(String email);

    void resetPassword(String token, String newPassword);

    User getCurrentUserName();

    User authenticate(String email, String password);

}
