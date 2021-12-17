package com.aacademy.realestate.controller;

import com.aacademy.realestate.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/images")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/estate/{estateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus>createImage(@RequestParam("file") MultipartFile file, @PathVariable Long estateId) {
        imageService.createImage(file, estateId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/estate/{estateId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<List<Resource>> findAllImages(@PathVariable Long estateId) {
        return ResponseEntity.ok(imageService.findAllImages(estateId));
    }
}
