package com.aacademy.realestate.repository;

import com.aacademy.realestate.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByEstateId(Long estateId);
}
