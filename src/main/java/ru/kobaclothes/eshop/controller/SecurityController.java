package ru.kobaclothes.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/register")
    public String showRegistrationForm() {

        return null;
    }
}
