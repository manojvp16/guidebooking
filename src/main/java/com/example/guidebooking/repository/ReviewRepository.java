package com.example.guidebooking.repository;

import com.example.guidebooking.entity.Review;
import com.example.guidebooking.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByGuide(Guide guide);
}