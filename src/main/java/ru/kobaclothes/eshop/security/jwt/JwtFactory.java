package ru.kobaclothes.eshop.security.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.kobaclothes.eshop.model.Role;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.model.UserStatus;

import java.util.ArrayList;
import java.util.List;

public final class JwtFactory {
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getDeliveryInfo(),
                user.getUserStatus().equals(UserStatus.ACTIVE),
                mapRolesToAuthorities(user.getRole()) //Возможно ошибка
        );
    }
    public static List<SimpleGrantedAuthority> mapRolesToAuthorities(Role role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }
}
