package ru.kobaclothes.eshop.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.dao.UserRepository;
import ru.kobaclothes.eshop.dto.UserDto;
import ru.kobaclothes.eshop.model.Role;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.model.UserStatus;
import ru.kobaclothes.eshop.service.interfaces.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //User вместо UserDto
    @Override
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
    }
    //TODO
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByEmail(String name) {
        return userRepository.findByEmail(name);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
