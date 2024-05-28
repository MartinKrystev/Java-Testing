package com.test.web;

import com.test.secret.PrivateData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@CrossOrigin
public class UploadController {

    @GetMapping("/upload")
    public String getFileUpload() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "upload-image";
        }

        try {
            // Define the upload directory (absolute path - constant)
            String uploadDir = PrivateData.UPLOADS_DIR;

            // Create the directory if it doesn't exist
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Save the file to the specified directory
            Path filePath = path.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            model.addAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }

        return "upload-image";
    }

}
