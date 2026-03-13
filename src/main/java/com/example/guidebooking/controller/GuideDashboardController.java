package com.example.guidebooking.controller;

import com.example.guidebooking.entity.Booking;
import com.example.guidebooking.entity.Guide;
import com.example.guidebooking.entity.User;
import com.example.guidebooking.service.BookingService;
import com.example.guidebooking.service.GuideService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GuideDashboardController {

    @Autowired private BookingService bookingService;
    @Autowired private GuideService guideService;

    @GetMapping("/guide/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        Guide guide = guideService.getGuideByUser(loggedUser);
        if (guide == null) return "redirect:/guide/register";

        session.setAttribute("loggedGuide", guide);
        List<Booking> bookings = bookingService.getGuideBookings(guide);
        model.addAttribute("guide",         guide);
        model.addAttribute("bookings",      bookings);
        model.addAttribute("totalBookings", bookings.size());
        return "guide/dashboard";
    }

    @PostMapping("/booking/accept/{id}")
    public String acceptBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            booking.setStatus("PAYMENT_PENDING");
            bookingService.updateBooking(booking);
        }
        return "redirect:/guide/dashboard";
    }

    @PostMapping("/booking/reject/{id}")
    public String rejectBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            booking.setStatus("REJECTED");
            bookingService.updateBooking(booking);
        }
        return "redirect:/guide/dashboard";
    }
}