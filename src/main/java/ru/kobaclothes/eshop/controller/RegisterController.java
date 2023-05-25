package ru.kobaclothes.eshop.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.service.implementations.EmailService;
import ru.kobaclothes.eshop.service.interfaces.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegisterController {

    private final UserService userService;
    private final EmailService emailService;
    private final Map<String, String> activationTokens = new HashMap<>();


    public RegisterController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "registration";
    }

    @PostMapping
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDTO userDTO) {
        try {
            userService.registerNewUserAccount(userDTO);
            // TODO
        } catch (Error error) {
            ModelAndView mav = new ModelAndView("registration", "user", userDTO);
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        } catch (RuntimeException runtimeException) {
            return new ModelAndView("emailError", "user", userDTO);
        }
        return new ModelAndView("home", "user", userDTO);
    }



}
