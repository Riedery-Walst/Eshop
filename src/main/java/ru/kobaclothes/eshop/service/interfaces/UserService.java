package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.dto.UserDto;
import ru.kobaclothes.eshop.model.User;

import java.util.List;

public interface UserService {
    User createUser(UserDto userDto);

    User getCurrentUser();

    User findUserByEmail(String email);

    void deleteById(Long id);

    User findById(Long id);

    List<User> getAll();
}
