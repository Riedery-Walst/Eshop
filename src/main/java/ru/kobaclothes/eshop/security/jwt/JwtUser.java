package ru.kobaclothes.eshop.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kobaclothes.eshop.model.DeliveryInfo;

import java.util.Collection;
import java.util.List;

public class JwtUser implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final boolean enabled;
    private final List<DeliveryInfo> deliveryInfo; //При необходимости удалить

    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String email, String password, List<DeliveryInfo> deliveryInfo, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.deliveryInfo = deliveryInfo;
        this.enabled = enabled;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
