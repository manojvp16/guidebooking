package com.example.guidebooking.repository;

import com.example.guidebooking.entity.Guide;
import com.example.guidebooking.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuideRepository extends JpaRepository<Guide, Long> {

    List<Guide> findByCity(String city);

    List<Guide> findByStatus(String status);
    
    Optional<Guide> findByUser(User user);

    long countByStatus(String status);

    List<Guide> findByCityContainingIgnoreCase(String city);

    Page<Guide> findByStatus(String status, Pageable pageable);
}