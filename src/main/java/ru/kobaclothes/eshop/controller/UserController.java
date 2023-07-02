package ru.kobaclothes.eshop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kobaclothes.eshop.dto.PasswordChangeDTO;
import ru.kobaclothes.eshop.dto.UserDTO;
import ru.kobaclothes.eshop.exception.InvalidTokenException;
import ru.kobaclothes.eshop.exception.PasswordMismatchException;
import ru.kobaclothes.eshop.exception.UserAlreadyExistException;
import ru.kobaclothes.eshop.exception.UserNotFoundException;
import ru.kobaclothes.eshop.service.interfaces.AccountInfoService;
import ru.kobaclothes.eshop.service.interfaces.UserService;

@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final AccountInfoService accountInfoService;

    public UserController(UserService userService, AccountInfoService accountInfoService) {
        this.userService = userService;
        this.accountInfoService = accountInfoService;
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
        try {
            userService.initiatePasswordReset(email);
            model.addAttribute("message", "Ссылка для сброса пароля отправлена на вашу электронную почту.");
            return "message";
        } catch (UserNotFoundException e) {
            model.addAttribute("error", "Пользователь с указанной электронной почтой не найден.");
            return "error";
        }
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @ModelAttribute("Password") @Valid PasswordChangeDTO request,
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
                                 @ModelAttribute("password") @Valid PasswordChangeDTO request,
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


/*    @PostMapping("/account/update")
    public String updateAccountInfo(HttpServletRequest request,
                                    @RequestParam("birthDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date birthDate,
                                    @RequestParam("gender") Gender gender) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email != null) {
            accountInfoService.setAccountInfo(email, birthDate, gender);
            return "Account information updated successfully";
        } else {
            return "Email not found in session";
        }
    }*/
}

