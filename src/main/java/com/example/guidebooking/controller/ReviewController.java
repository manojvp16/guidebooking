package com.example.guidebooking.controller;

import com.example.guidebooking.entity.*;
import com.example.guidebooking.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    @Autowired private BookingService bookingService;
    @Autowired private ReviewService reviewService;

    @GetMapping("/review/{bookingId}")
    public String reviewPage(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        model.addAttribute("booking", booking);
        return "review/review-form";
    }

    @PostMapping("/review/{bookingId}")
    public String submitReview(@PathVariable Long bookingId,
                               @RequestParam int rating,
                               @RequestParam String comment,
                               HttpSession session) {
        Booking booking = bookingService.getBookingById(bookingId);
        User user = (User) session.getAttribute("loggedUser");

        Review review = new Review();
        review.setGuide(booking.getGuide());
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);
        reviewService.saveReview(review);

        return "redirect:/my-bookings";
    }
}