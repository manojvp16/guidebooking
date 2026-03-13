package com.example.guidebooking.controller;

import com.example.guidebooking.entity.Booking;
import com.example.guidebooking.entity.Payment;
import com.example.guidebooking.service.BookingService;
import com.example.guidebooking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class PaymentController {

    @Autowired private BookingService bookingService;
    @Autowired private PaymentService paymentService;

    @GetMapping("/payment/{bookingId}")
    public String paymentPage(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null || !booking.getStatus().equals("PAYMENT_PENDING")) {
            return "redirect:/my-bookings";
        }
        model.addAttribute("booking", booking);
        return "payment/payment";
    }

    @PostMapping("/payment/success/{bookingId}")
    public String paymentSuccess(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getGuide().getPrice());
        payment.setStatus("SUCCESS");
        payment.setTransactionId("TXN" + System.currentTimeMillis());
        payment.setPaymentDate(LocalDateTime.now());
        paymentService.savePayment(payment);

        booking.setStatus("CONFIRMED");
        bookingService.updateBooking(booking);

        model.addAttribute("message", "Payment Successful");
        return "payment/payment-status";
    }

    @PostMapping("/payment/fail/{bookingId}")
    public String paymentFail(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getGuide().getPrice());
        payment.setStatus("FAILED");
        payment.setTransactionId("TXN" + System.currentTimeMillis());
        payment.setPaymentDate(LocalDateTime.now());
        paymentService.savePayment(payment);

        model.addAttribute("message", "Payment Failed");
        return "payment/payment-status";
    }
}