package com.aacademy.realestate.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String store(MultipartFile multipartFile);

    Resource loadAsResource(String fileName);
}
