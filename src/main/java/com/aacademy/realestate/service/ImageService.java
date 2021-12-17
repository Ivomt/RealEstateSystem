package com.aacademy.realestate.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    void createImage(MultipartFile file, Long estateId);

    List<Resource> findAllImages(Long estateId);
}
