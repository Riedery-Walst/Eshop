package ru.kobaclothes.eshop.service.implementations;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.dto.PasswordChangeDTO;
import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.exception.*;
import ru.kobaclothes.eshop.model.Role;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.model.UserToken;
import ru.kobaclothes.eshop.model.UserTokenType;
import ru.kobaclothes.eshop.repository.UserRepository;
import ru.kobaclothes.eshop.repository.UserTokenRepository;
import ru.kobaclothes.eshop.service.interfaces.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);

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
        emailVerificationToken.setExpireDate(LocalDateTime.now().plusHours(24)); // Токен будет действителен в течение 24 часов
        emailVerificationToken.setUserTokenType(UserTokenType.EMAIL_VERIFICATION);
        userTokenRepository.save(emailVerificationToken);

        emailService.sendConfirmationEmail(user.getEmail(), verificationToken);
    }

    @Override
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found for email: " + email);
        }

        String resetToken = generateToken();
        UserToken passwordResetToken = new UserToken();
        passwordResetToken.setToken(resetToken);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpireDate(LocalDateTime.now().plusHours(24));
        passwordResetToken.setUserTokenType(UserTokenType.PASSWORD_RESET);
        userTokenRepository.save(passwordResetToken);

        emailService.sendForgotPassword(user.getEmail(), resetToken);
    }


    @Override
    public void setPasswordByToken(String token, PasswordChangeDTO passwordChangeDTO) {
        UserToken userToken = userTokenRepository.findByTokenAndUserTokenType(token, UserTokenType.PASSWORD_RESET);

        if (userToken == null || userToken.getExpireDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Invalid or expired password reset token.");
        }

        User user = userToken.getUser();

        if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect.");
        }

        if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getMatchingPassword())) {
            throw new InvalidPasswordException("New password and matching password do not match.");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(user);
        userTokenRepository.delete(userToken);
    }


    @Override
    public void verifyAccountByToken(String token) {
        UserToken userToken = userTokenRepository.findByTokenAndUserTokenType(token, UserTokenType.EMAIL_VERIFICATION);
        if (userToken != null && userToken.getExpireDate().isAfter(LocalDateTime.now())) {
            User user = userToken.getUser();
            user.setVerified(true);
            userRepository.save(user);
            userTokenRepository.delete(userToken);
        } else {
            throw new InvalidTokenException("Invalid or expired token.");
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

    private String generateToken() {
        UUID uuid = UUID.randomUUID();
        // Extract the first 6 characters from the UUID
        return uuid.toString().replaceAll("-", "").substring(0, 4);
    }


}