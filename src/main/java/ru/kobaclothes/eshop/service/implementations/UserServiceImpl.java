package ru.kobaclothes.eshop.service.implementations;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.exception.InvalidTokenException;
import ru.kobaclothes.eshop.exception.UserNotFoundException;
import ru.kobaclothes.eshop.model.PasswordResetToken;
import ru.kobaclothes.eshop.model.Role;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.model.UserStatus;
import ru.kobaclothes.eshop.repository.PasswordResetTokenRepository;
import ru.kobaclothes.eshop.repository.UserRepository;
import ru.kobaclothes.eshop.service.interfaces.UserService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, PasswordResetTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void registerNewUserAccount(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("There is an account with that email address: " + userDTO.getEmail());
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setRole(Role.ROLE_USER);
        user.setVerified(false);

        String verificationCode = generateCode();
        user.setVerificationCode(verificationCode);
        emailService.sendConfirmationEmail(user.getEmail(), verificationCode);

        userRepository.save(user);

    }

    @Override
    public void verifyEmail(String code) {
        Optional<User> optionalUser = userRepository.findByVerificationCode(code);
        optionalUser.ifPresent(user -> {
            user.setVerified(true);
            user.setVerificationCode(null);
            userRepository.save(user);
        });
    }

    @Override
    public void changePassword(String email, String newPassword, String currentPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is not correct");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        // Generate a random token
        String token = generateCode();

        // Create a PasswordResetToken and associate it with the user
        PasswordResetToken resetToken = new PasswordResetToken(user, token);
        tokenRepository.save(resetToken);

        // Send the reset token via email or SMS
        emailService.sendForgotPassword(user.getEmail(), token);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        // Find the PasswordResetToken by token
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Invalid or expired token");
        }

        // Get the associated user
        User user = resetToken.getUser();

        // Update the user's password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete the used token
        tokenRepository.delete(resetToken);
    }

    @Override
    public User getCurrentUserName() {
        return null;
    }

    @Override
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private String generateCode() {
        UUID uuid = UUID.randomUUID();
        // Extract the first 6 characters from the UUID
        return uuid.toString().replaceAll("-", "").substring(0, 6);
    }

}