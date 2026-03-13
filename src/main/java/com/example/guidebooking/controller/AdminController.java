package com.example.guidebooking.controller;

import com.example.guidebooking.entity.Guide;
import com.example.guidebooking.repository.BookingRepository;
import com.example.guidebooking.repository.GuideRepository;
import com.example.guidebooking.repository.PaymentRepository;
import com.example.guidebooking.service.BookingService;
import com.example.guidebooking.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private GuideService guideService;
    @Autowired private BookingService bookingService;
    @Autowired private GuideRepository guideRepository;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private PaymentRepository paymentRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        long totalGuides       = guideRepository.countByStatus("APPROVED");
        long totalBookings     = bookingRepository.count();
        long confirmedBookings = bookingRepository.countByStatus("CONFIRMED");
        Double revenue         = paymentRepository.getTotalRevenue();
        if (revenue == null) revenue = 0.0;
        model.addAttribute("totalGuides",       totalGuides);
        model.addAttribute("totalBookings",     totalBookings);
        model.addAttribute("confirmedBookings", confirmedBookings);
        model.addAttribute("revenue",           revenue);
        return "admin/dashboard";
    }

    @GetMapping("/guides")
    public String pendingGuides(Model model) {
        model.addAttribute("guides", guideService.getPendingGuides());
        return "admin/pending-guides";
    }

    @GetMapping("/approve/{id}")
    public String approveGuide(@PathVariable Long id) {
        Guide guide = guideService.getGuideById(id);
        if (guide != null) {
            guide.setStatus("APPROVED");
            guideService.saveGuide(guide);
        }
        return "redirect:/admin/guides";
    }

    @GetMapping("/guide/edit/{id}")
    public String editGuide(@PathVariable Long id, Model model) {
        model.addAttribute("guide", guideService.getGuideById(id));
        return "admin/edit-guide";
    }

    @PostMapping("/guide/update")
    public String updateGuide(@ModelAttribute Guide guide) {
        guideService.saveGuide(guide);
        return "redirect:/admin/guides";
    }

    @GetMapping("/guide/delete/{id}")
    public String deleteGuide(@PathVariable Long id) {
        guideService.deleteGuide(id);
        return "redirect:/admin/guides";
    }

    @GetMapping("/bookings")
    public String viewBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin/bookings";
    }
}