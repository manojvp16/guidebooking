package com.example.guidebooking.service;

import com.example.guidebooking.entity.Guide;
import com.example.guidebooking.entity.User;
import com.example.guidebooking.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideService {

    @Autowired
    private GuideRepository guideRepository;

    public Page<Guide> getApprovedGuides(Pageable pageable) {
        return guideRepository.findByStatus("APPROVED", pageable);
    }

    public Guide getGuideById(Long id) {
        return guideRepository.findById(id).orElse(null);
    }

    public void saveGuide(Guide guide) {
        guideRepository.save(guide);
    }

    public List<Guide> getPendingGuides() {
        return guideRepository.findByStatus("PENDING");
    }

    public void deleteGuide(Long id) {
        guideRepository.deleteById(id);
    }

    public Guide getGuideByUser(User user) {
        return guideRepository.findByUser(user).orElse(null);
    }

    public List<Guide> searchByCity(String city) {
        return guideRepository.findByCityContainingIgnoreCase(city);
    }
}