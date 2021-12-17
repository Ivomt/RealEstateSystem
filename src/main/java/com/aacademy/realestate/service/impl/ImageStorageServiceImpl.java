package com.aacademy.realestate.service.impl;

import com.aacademy.realestate.exception.StorageException;
import com.aacademy.realestate.service.StorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageStorageServiceImpl implements StorageService {

    private static final List<String> SUPPORTED_FILE_EXTENSIONS = Arrays.asList("jpg", "png");
    private static final String ROOT_DIRECTORY = "C:\\Users\\Teachers";

    @Autowired
    private final ResourceLoader resourceLoader;

    @Override
    public String store(MultipartFile file) {
        Objects.requireNonNull(file);
        String newFileName = generateFileName(file.getOriginalFilename());

        try {
            if (file.isEmpty() || ImageIO.read(file.getInputStream()) == null
                    || !FilenameUtils.isExtension(file.getOriginalFilename(), SUPPORTED_FILE_EXTENSIONS)) {
                throw new StorageException();
            }

            File destination = FileUtils.getFile(ROOT_DIRECTORY, newFileName);
            file.transferTo(destination);

        } catch (IOException ioException) {
            throw new StorageException();
        }

        return newFileName;
    }

    @Override
    public Resource loadAsResource(String fileName) {
        return resourceLoader.getResource(ResourceUtils.FILE_URL_PREFIX + getFile(fileName).toString());
    }

    private String generateFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String fileExtension = FilenameUtils.getExtension(originalFileName);

        return String.format("%s.%s", uuid, fileExtension);
    }

    private File getFile(String fileName) {
        File file;
        if (StringUtils.isBlank(fileName) ||
                (file = FileUtils.getFile(ROOT_DIRECTORY, fileName)).isDirectory()) {
            throw new StorageException();
        }

        return file;
    }
}
