package ru.kobaclothes.eshop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.exception.InvalidTokenException;
import ru.kobaclothes.eshop.exception.PasswordMismatchException;
import ru.kobaclothes.eshop.exception.UserAlreadyExistException;
import ru.kobaclothes.eshop.model.User;
import ru.kobaclothes.eshop.request.PasswordChangeRequest;
import ru.kobaclothes.eshop.service.interfaces.UserService;

@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSingUpForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "Signup";
    }

    @PostMapping("/signup")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDTO userDTO) {
        try {
            userService.registerNewUserAccount(userDTO);
        } catch (UserAlreadyExistException error) {
            ModelAndView mav = new ModelAndView("registration", "user", userDTO);
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }
        return new ModelAndView("home", "user", userDTO);
    }

    @PostMapping("/verify-account")
    public String verifyAccount(@RequestParam("token") String token, Model model) {
        try {
            userService.verifyAccountByToken(token);
            model.addAttribute("message", "Аккаунт успешно верифицирован.");
            return "message";
        } catch (InvalidTokenException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/initiate-password-reset")
    public String initiatePasswordReset(@RequestParam("email") String email, Model model) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            userService.initiatePasswordReset(user);
            model.addAttribute("message", "Ссылка для сброса пароля отправлена на вашу электронную почту.");
            return "message";
        } else {
            model.addAttribute("error",     "Пользователь с указанной электронной почтой не найден.");
            return "error";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @ModelAttribute("Password") @Valid PasswordChangeRequest request,
                                Model model) {
        if (!request.getNewPassword().equals(request.getMatchingPassword())) {
            model.addAttribute("error", "Passwords do not match.");
            return "error";
        }
        try {
            userService.setPasswordByToken(token, request);
            model.addAttribute("message", "Пароль успешно сброшен.");
            return "message";
        } catch (InvalidTokenException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/security")
    public String changePassword(HttpSession session,
                                 @ModelAttribute("password") @Valid PasswordChangeRequest request,
                                 Model model) {
        if (!request.getNewPassword().equals(request.getMatchingPassword())) {
            model.addAttribute("error", "Passwords do not match.");
            return "error";
        }
        try {
            userService.changePassword((String) session.getAttribute("email"), request.getNewPassword(), request.getCurrentPassword());
            model.addAttribute("message", "Пароль успешно изменен.");
            return "redirect:/account";
        } catch (PasswordMismatchException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}

