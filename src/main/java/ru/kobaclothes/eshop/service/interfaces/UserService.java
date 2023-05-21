package ru.kobaclothes.eshop.service.interfaces;

import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.model.User;

public interface UserService {
    User createUser(UserDTO userDto);

    User getCurrentUser();

    void loginUser(UserDTO UserDto);

    void logoutUser();
}
