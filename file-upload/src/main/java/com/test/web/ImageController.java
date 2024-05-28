package com.test.web;

import com.test.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {

    private StorageService storageService;

    @Autowired
    public ImageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        String uploadImage = storageService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) {

        byte[] imageData = storageService.downloadImage(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

}
