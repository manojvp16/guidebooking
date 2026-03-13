package com.example.guidebooking.controller;

import com.example.guidebooking.entity.Guide;
import com.example.guidebooking.entity.User;
import com.example.guidebooking.service.AuthService;
import com.example.guidebooking.service.GuideService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired private AuthService authService;
    @Autowired private GuideService guideService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user, Model model) {
        authService.registerUser(user);
        model.addAttribute("message", "Registration successful! Please login.");
        return "auth/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login-success")
    public String loginSuccess(Authentication authentication, HttpSession session) {
        String username = authentication.getName();
        User user = authService.getUserByUsername(username);
        session.setAttribute("loggedUser", user);

        switch (user.getRole()) {
            case "GUIDE" -> {
                Guide guide = guideService.getGuideByUser(user);
                if (guide != null) {
                    session.setAttribute("loggedGuide", guide);
                    return "redirect:/guide/dashboard";
                } else {
                    // Registered as GUIDE but no profile yet — send to profile form
                    return "redirect:/guide/register";
                }
            }
            case "ADMIN" -> { return "redirect:/admin/dashboard"; }
            default      -> { return "redirect:/my-bookings"; }
        }
    }
}