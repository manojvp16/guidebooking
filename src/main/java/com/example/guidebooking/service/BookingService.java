package com.example.guidebooking.service;

import com.example.guidebooking.entity.Booking;
import com.example.guidebooking.entity.Guide;
import com.example.guidebooking.entity.User;
import com.example.guidebooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(User user) {
        return bookingRepository.findByUserOrderByBookingDateDesc(user);
    }

    public List<Booking> getGuideBookings(Guide guide) {
        return bookingRepository.findByGuide(guide);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void updateBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}