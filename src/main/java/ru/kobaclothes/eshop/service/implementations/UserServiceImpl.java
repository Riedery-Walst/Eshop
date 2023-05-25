package ru.kobaclothes.eshop.service.implementations;

import jakarta.transaction.Transactional;
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
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean registerNewUserAccount(UserDTO userDTO) {
        if (emailExists(userDTO.getEmail())) {
            throw new RuntimeException("There is an account with that email address: " + userDTO.getEmail());
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setRole(Role.USER);
        userRepository.save(user);
        return true;
    }

    private boolean emailExists(final String email) {
            return userRepository.findByEmail(email) != null;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "No user found with username: " + email);
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(), enabled, accountNonExpired,
                    credentialsNonExpired, accountNonLocked,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
            );
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        }
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