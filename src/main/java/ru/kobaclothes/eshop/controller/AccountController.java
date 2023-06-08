package ru.kobaclothes.eshop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.service.interfaces.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify/{code}")
    public String verifyEmail(@PathVariable("code") String code, RedirectAttributes redirectAttributes) {
        userService.verifyEmail(code);
        redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully.");
        return "redirect:/account";
    }

    // Handler for displaying the user security form
    @GetMapping("/security")
    public String showUserSecurity() {
        return "security";
    }

    // Handler for submitting the change password form
    @PostMapping("/security")
    public String changePassword(
            HttpSession session,
            @ModelAttribute("password") @Valid UserDTO userDTO,
            RedirectAttributes redirectAttributes) {
        userService.changePassword((String) session.getAttribute("email"), userDTO.getNewPassword(), userDTO.getCurrentPassword());
        redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully.");
        return "redirect:/account";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
