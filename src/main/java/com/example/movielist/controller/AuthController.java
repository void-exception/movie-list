package com.example.movielist.controller;


import com.example.movielist.model.User;
import com.example.movielist.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String root(HttpSession session) {
        return session.getAttribute("userId") != null
                ? "redirect:/movies" : "redirect:/login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String login,
                           @RequestParam String password,
                           Model model) {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("error", "Заполните все поля");
            return "register";
        }
        if (userRepository.existsByLogin(login)) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "register";
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Optional<User> userOpt = userRepository.findByLogin(login);
        if (userOpt.isEmpty() || !encoder.matches(password, userOpt.get().getPassword())) {
            model.addAttribute("error", "Неверный логин или пароль");
            return "login";
        }
        session.setAttribute("userId", userOpt.get().getId());
        session.setAttribute("login", userOpt.get().getLogin());
        return "redirect:/movies";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
