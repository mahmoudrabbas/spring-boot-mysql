package com.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    public static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImages(@RequestParam MultipartFile file){
        try {
            // create the directory
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // save file locally
            Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            return ResponseEntity.status(200).body(file.getOriginalFilename()+" Uploaded Successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
