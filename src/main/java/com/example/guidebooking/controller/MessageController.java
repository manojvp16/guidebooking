package com.example.guidebooking.controller;

import com.example.guidebooking.entity.Booking;
import com.example.guidebooking.entity.Message;
import com.example.guidebooking.entity.User;
import com.example.guidebooking.service.BookingService;
import com.example.guidebooking.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class MessageController {

    @Autowired private BookingService bookingService;
    @Autowired private MessageService messageService;

    @GetMapping("/chat/{bookingId}")
    public String chatPage(@PathVariable Long bookingId,
                           Model model, Principal principal, HttpSession session) {

        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) return "redirect:/";

        String username = principal.getName();
        boolean isTourist = booking.getUser().getUsername().equals(username);
        boolean isGuide   = booking.getGuide().getUser() != null &&
                            booking.getGuide().getUser().getUsername().equals(username);

        if (!isTourist && !isGuide) return "redirect:/";

        messageService.markAsRead(booking, username);
        List<Message> messages = messageService.getMessages(booking);
        model.addAttribute("booking",  booking);
        model.addAttribute("messages", messages);
        model.addAttribute("me",       username);
        return "chat/chat";
    }

    @PostMapping("/chat/{bookingId}/send")
    public String sendMessage(@PathVariable Long bookingId,
                              @RequestParam String content,
                              Principal principal, HttpSession session) {

        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null || content.isBlank()) return "redirect:/chat/" + bookingId;

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        String username = principal.getName();
        boolean isTourist = booking.getUser().getUsername().equals(username);
        boolean isGuide   = booking.getGuide().getUser() != null &&
                            booking.getGuide().getUser().getUsername().equals(username);
        if (!isTourist && !isGuide) return "redirect:/";

        messageService.sendMessage(booking, loggedUser, content.trim());
        return "redirect:/chat/" + bookingId;
    }
}