package com.example.guidebooking.controller;

import com.example.guidebooking.entity.*;
import com.example.guidebooking.repository.*;
import com.example.guidebooking.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class BookingController {

    @Autowired private GuideRepository guideRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookingService bookingService;

    @GetMapping("/book/{guideId}")
    public String bookingForm(@PathVariable Long guideId, Model model) {
        Guide guide = guideRepository.findById(guideId).orElse(null);
        model.addAttribute("guide", guide);
        model.addAttribute("booking", new Booking());
        return "booking/booking-form";
    }

    @PostMapping("/book/{guideId}")
    public String confirmBooking(@PathVariable Long guideId,
                                 @ModelAttribute Booking booking,
                                 Principal principal) {
        Guide guide = guideRepository.findById(guideId).orElse(null);
        User user   = userRepository.findByUsername(principal.getName()).orElse(null);

        booking.setGuide(guide);
        booking.setUser(user);
        booking.setStatus("PENDING");
        bookingService.saveBooking(booking);

        return "booking/booking-success";
    }

    @GetMapping("/customer/dashboard")
    public String customerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("bookings", bookingService.getUserBookings(user));
        return "dashboard/customer";
    }
}