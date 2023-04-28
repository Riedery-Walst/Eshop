package ru.kobaclothes.eshop.dao;

import ru.kobaclothes.eshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
