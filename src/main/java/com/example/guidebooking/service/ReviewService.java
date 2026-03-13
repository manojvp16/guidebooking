package com.example.guidebooking.service;

import com.example.guidebooking.entity.Guide;
import com.example.guidebooking.entity.Review;
import com.example.guidebooking.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getGuideReviews(Guide guide) {
        return reviewRepository.findByGuide(guide);
    }

    public double getAverageRating(Guide guide) {
        List<Review> reviews = reviewRepository.findByGuide(guide);
        if (reviews.isEmpty()) return 0;
        int total = 0;
        for (Review r : reviews) total += r.getRating();
        return (double) total / reviews.size();
    }

    public int getReviewCount(Guide guide) {
        return reviewRepository.findByGuide(guide).size();
    }
}