package com.example.guidebooking.repository;

import com.example.guidebooking.entity.Booking;
import com.example.guidebooking.entity.Guide;
import com.example.guidebooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    List<Booking> findByGuide(Guide guide);

    long count();

    long countByStatus(String status);

    List<Booking> findByUserOrderByBookingDateDesc(User user);
}