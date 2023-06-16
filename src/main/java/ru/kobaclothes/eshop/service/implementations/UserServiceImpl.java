package ru.kobaclothes.eshop.service.implementations;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.exception.*;
import ru.kobaclothes.eshop.model.*;
import ru.kobaclothes.eshop.repository.UserRepository;
import ru.kobaclothes.eshop.repository.UserTokenRepository;
import ru.kobaclothes.eshop.request.PasswordChangeRequest;
import ru.kobaclothes.eshop.service.interfaces.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserTokenRepository userTokenRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, UserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public void registerNewUserAccount(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userDTO.getEmail());
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
        user.setActiveStatus(true);
        user.setRole(Role.ROLE_USER);
        user.setVerified(false);

        initiateEmailVerification(user);

        userRepository.save(user);

    }

    @Override
    public void initiateEmailVerification(User user) {
        String verificationToken = generateToken();
        UserToken emailVerificationToken = new UserToken();
        emailVerificationToken.setToken(verificationToken);
        emailVerificationToken.setUser(user);
        emailVerificationToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Токен будет действителен в течение 24 часов
        emailVerificationToken.setUserTokenType(UserTokenType.EMAIL_VERIFICATION);
        userTokenRepository.save(emailVerificationToken);

        emailService.sendConfirmationEmail(user.getEmail(), verificationToken);
    }

    @Override
    public void initiatePasswordReset(User user) {
        String resetToken = generateToken();
        UserToken passwordResetToken = new UserToken();
        passwordResetToken.setToken(resetToken);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Токен будет действителен в течение 24 часов
        passwordResetToken.setUserTokenType(UserTokenType.PASSWORD_RESET);
        userTokenRepository.save(passwordResetToken);

        emailService.sendForgotPassword(user.getEmail(), resetToken);
    }

    @Override
    public void setPasswordByToken(String token, PasswordChangeRequest passwordChangeRequest) {
        UserToken userToken = userTokenRepository.findByTokenAndUserTokenType(token, UserTokenType.PASSWORD_RESET);
        if (userToken != null && userToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            User user = userToken.getUser();

            if (passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) {
                if (passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getMatchingPassword())) {
                    user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
                    userRepository.save(user);
                    userTokenRepository.delete(userToken);
                } else {
                    throw new InvalidPasswordException("New password and matching password do not match.");
                }
            } else {
                throw new InvalidPasswordException("Current password is incorrect.");
            }
        } else {
            throw new InvalidTokenException("Invalid or expired password reset token.");
        }
    }

    @Override
    public void verifyAccountByToken(String token) {
        UserToken userToken = userTokenRepository.findByTokenAndUserTokenType(token, UserTokenType.EMAIL_VERIFICATION);
        if (userToken != null && userToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            User user = userToken.getUser();
            user.setVerified(true);
            userRepository.save(user);
            userTokenRepository.delete(userToken);
        } else {
            throw new InvalidTokenException("Недействительный или просроченный токен для верификации аккаунта.");
        }
    }

    @Override
    public void changePassword(String email, String newPassword, String currentPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordMismatchException("Current password is not correct");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    

    @Override
    public User getCurrentUserName() {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private String generateToken() {
        UUID uuid = UUID.randomUUID();
        // Extract the first 6 characters from the UUID
        return uuid.toString().replaceAll("-", "").substring(0, 4);
    }


}