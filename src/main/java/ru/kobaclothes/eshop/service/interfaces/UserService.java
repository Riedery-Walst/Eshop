package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.model.User;

public interface UserService {
    User createUser(User user);

    User getCurrentUser();

    User findUserByEmail(String email);
}
