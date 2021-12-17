package com.aacademy.realestate.service.impl;

import com.aacademy.realestate.exception.ResourceNotFoundException;
import com.aacademy.realestate.model.Estate;
import com.aacademy.realestate.model.Image;
import com.aacademy.realestate.repository.EstateRepository;
import com.aacademy.realestate.repository.ImageRepository;
import com.aacademy.realestate.service.ImageService;
import com.aacademy.realestate.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final StorageService storageService;
    private final ImageRepository imageRepository;
    private final EstateRepository estateRepository;

    @Override
    public void createImage(MultipartFile file, Long estateId) {
        Estate estate = estateRepository.findById(estateId)
                .orElseThrow(() -> new ResourceNotFoundException("estate does not exists with id: " + estateId));
        String fileName = storageService.store(file);
        imageRepository.save(Image.builder()
                .estate(estate)
                .name(fileName)
                .build());
    }

    @Override
    public List<Resource> findAllImages(Long estateId) {
        return imageRepository.findAllByEstateId(estateId)
                .stream()
                .map(image -> storageService.loadAsResource(image.getName()))
                .collect(Collectors.toList());
    }
}
