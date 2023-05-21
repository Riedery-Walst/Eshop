package ru.kobaclothes.eshop.service.implementations;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.model.Role;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.model.UserStatus;
import ru.kobaclothes.eshop.repository.UserRepository;
import ru.kobaclothes.eshop.service.interfaces.UserService;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserDTO userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
    @Override
    public void loginUser(UserDTO UserDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            UserDetails userDetails = loadUserByUsername(UserDto.getEmail());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails, UserDto.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }

    @Override
    public void logoutUser() {
        SecurityContextHolder.clearContext();
    }

}