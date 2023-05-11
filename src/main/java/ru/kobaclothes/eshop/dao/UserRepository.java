package ru.kobaclothes.eshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kobaclothes.eshop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
