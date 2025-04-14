package com.store.controller;

import com.store.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadFile(@PathVariable Long id, @RequestParam String pathType, @RequestParam MultipartFile file){
        return ResponseEntity.ok(fileUploadService.storeFile(fileUploadService.convertMultiPartToFile(file), id, pathType));
    }
}
