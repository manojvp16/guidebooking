package com.example.guidebooking.repository;

import com.example.guidebooking.entity.Booking;
import com.example.guidebooking.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByBookingOrderBySentAtAsc(Booking booking);

    long countByBookingAndReadByRecipientFalseAndSenderUsernameNot(
            Booking booking, String senderUsername);
}