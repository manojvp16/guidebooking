package com.example.guidebooking.controller;

import com.example.guidebooking.entity.Guide;
import com.example.guidebooking.entity.User;
import com.example.guidebooking.service.GuideService;
import com.example.guidebooking.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GuideController {

    @Autowired private GuideService guideService;
    @Autowired private ReviewService reviewService;

    @GetMapping("/guides")
    public String listGuides(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Guide> guidePage = guideService.getApprovedGuides(PageRequest.of(page, 6));
        model.addAttribute("guides",      guidePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages",  guidePage.getTotalPages());
        return "guide/guide-list";
    }

    @GetMapping("/guide/{id}")
    public String guideDetails(@PathVariable Long id, Model model) {
        Guide guide = guideService.getGuideById(id);
        if (guide == null) return "redirect:/guides";
        model.addAttribute("guide",       guide);
        model.addAttribute("reviews",     reviewService.getGuideReviews(guide));
        model.addAttribute("rating",      reviewService.getAverageRating(guide));
        model.addAttribute("reviewCount", reviewService.getReviewCount(guide));
        return "guide/guide-details";
    }

    @GetMapping("/guide/register")
    public String showGuideForm(Model model) {
        model.addAttribute("guide", new Guide());
        return "guide/guide-register";
    }

    /**
     * FIX: After saving the profile, immediately set loggedGuide in session
     * so the dashboard works without needing to re-login.
     */
    @PostMapping("/guide/register")
    public String registerGuide(@ModelAttribute Guide guide, HttpSession session) {
        guide.setStatus("PENDING");
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            guide.setUser(loggedUser);
        }
        guideService.saveGuide(guide);
        if (loggedUser != null) {
            Guide saved = guideService.getGuideByUser(loggedUser);
            session.setAttribute("loggedGuide", saved);
        }
        return "redirect:/guide/dashboard";
    }

    @GetMapping("/guides/search")
    public String searchGuides(@RequestParam String city, Model model) {
        model.addAttribute("guides",      guideService.searchByCity(city));
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages",  1);
        return "guide/guide-list";
    }
}