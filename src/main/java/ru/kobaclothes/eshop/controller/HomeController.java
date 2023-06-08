package ru.kobaclothes.eshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    @GetMapping
    public String showRegistrationForm(HttpSession session) {
        String name = (String) session.getAttribute("email");
        System.out.println("Name attribute: " + name);
        return "home";
    }
}
