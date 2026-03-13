package com.example.guidebooking.service;

import com.example.guidebooking.entity.Booking;
import com.example.guidebooking.entity.Message;
import com.example.guidebooking.entity.User;
import com.example.guidebooking.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired private MessageRepository messageRepository;

    public Message sendMessage(Booking booking, User sender, String content) {
        Message msg = new Message();
        msg.setBooking(booking);
        msg.setSender(sender);
        msg.setContent(content);
        msg.setSentAt(LocalDateTime.now());
        msg.setReadByRecipient(false);
        return messageRepository.save(msg);
    }

    public List<Message> getMessages(Booking booking) {
        return messageRepository.findByBookingOrderBySentAtAsc(booking);
    }

    public void markAsRead(Booking booking, String viewerUsername) {
        List<Message> unread = messageRepository
                .findByBookingOrderBySentAtAsc(booking)
                .stream()
                .filter(m -> !m.isReadByRecipient()
                          && !m.getSender().getUsername().equals(viewerUsername))
                .toList();
        unread.forEach(m -> m.setReadByRecipient(true));
        messageRepository.saveAll(unread);
    }
}