package com.test.service;

import com.test.entity.ImageData;
import com.test.repository.StorageRepository;
import com.test.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {

    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }


    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = storageRepository.save(ImageData.builder()
                .name(file.getName())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());

        if (imageData != null) {
            return "file uploaded successfully: " + file.getOriginalFilename();
        }

        return null;
    }

    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);

        byte[] image = ImageUtils.decompressImage(dbImageData.get().getImageData());

        return image;
    }
}
