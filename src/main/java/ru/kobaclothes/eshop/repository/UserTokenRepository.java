package ru.kobaclothes.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kobaclothes.eshop.model.UserToken;
import ru.kobaclothes.eshop.model.UserTokenType;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    UserToken findByTokenAndUserTokenType(String token, UserTokenType userTokenType);
}