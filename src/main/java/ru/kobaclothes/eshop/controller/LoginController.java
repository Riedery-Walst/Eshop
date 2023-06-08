package ru.kobaclothes.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kobaclothes.eshop.exception.InvalidTokenException;
import ru.kobaclothes.eshop.exception.UserNotFoundException;
import ru.kobaclothes.eshop.service.interfaces.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showLoginForm() {
        return "login"; // Return the view name for the forgot password form
    }

    // Handler for displaying the forgot password form
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password"; // Return the view name for the forgot password form
    }

    // Handler for submitting the forgot password form
    @PostMapping("/forgot-password")
    public String submitForgotPasswordForm(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        try {
            userService.initiatePasswordReset(email);
            redirectAttributes.addFlashAttribute("successMessage", "Password reset email sent");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/forgot-password";
    }

    // Handler for displaying the password reset form
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    // Handler for submitting the password reset form
    @PostMapping("/reset-password")
    public String submitResetPasswordForm(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes) {
        try {
            userService.resetPassword(token, newPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Password reset successful");
        } catch (InvalidTokenException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/forgot-password";
        }
        return "redirect:/login";
    }
}
