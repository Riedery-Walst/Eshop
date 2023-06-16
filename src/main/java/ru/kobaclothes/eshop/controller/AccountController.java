package ru.kobaclothes.eshop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kobaclothes.eshop.exception.PasswordMismatchException;
import ru.kobaclothes.eshop.request.PasswordChangeRequest;
import ru.kobaclothes.eshop.service.interfaces.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }



    // Handler for displaying the user security form
    @GetMapping("/security")
    public String showUserSecurity(Model model) {
        model.addAttribute("password", new PasswordChangeRequest());
        return "security";
    }

    // Handler for submitting the change password form
    @PostMapping("/security")
    public String changePassword(
            HttpSession session,
            @ModelAttribute("password") @Valid PasswordChangeRequest request) {
        if (!request.getNewPassword().equals(request.getMatchingPassword())) {
            throw new PasswordMismatchException("Passwords do not match.");
        }
        userService.changePassword((String) session.getAttribute("email"), request.getNewPassword(), request.getCurrentPassword());
        return "redirect:/account";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
