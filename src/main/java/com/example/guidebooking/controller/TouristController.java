package com.example.guidebooking.controller;

import com.example.guidebooking.entity.*;
import com.example.guidebooking.repository.UserRepository;
import com.example.guidebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class TouristController {

    @Autowired private BookingService bookingService;
    @Autowired private UserRepository userRepository;

    @GetMapping("/my-bookings")
    public String myBookings(Model model, Principal principal) {
        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> bookings = bookingService.getUserBookings(user);
        model.addAttribute("bookings", bookings);
        return "tourist/my-bookings";
    }
}