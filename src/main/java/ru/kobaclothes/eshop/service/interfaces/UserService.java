package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.request.PasswordChangeRequest;

public interface UserService {


    void registerNewUserAccount(UserDTO userDTO);

    void initiateEmailVerification(User user);

    void initiatePasswordReset(User user);

    void setPasswordByToken(String token, PasswordChangeRequest passwordChangeRequest);

    void verifyAccountByToken(String token);

    void changePassword(String email, String newPassword, String currentPassword);

    User getCurrentUserName();

    User getUserByEmail(String email);
}
