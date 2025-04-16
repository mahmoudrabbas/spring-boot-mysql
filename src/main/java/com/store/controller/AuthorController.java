package com.store.controller;

import com.store.entity.Author;
import com.store.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.UIResource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Value("${file.upload.base-path}")
    private String imgDir;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.findById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addOne(@RequestPart("author") @Valid Author author, @RequestPart("file") MultipartFile file){

        author.setImagePath(authorService.uploadImg(file));

        return ResponseEntity.ok(authorService.save(author));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateOne(@RequestPart("author") @Valid Author entity, @RequestPart("file") MultipartFile file) {
        Author author = authorService.findById(entity.getId()).orElseThrow();
        author.setFullName(entity.getFullName());
        author.setEmail(entity.getEmail());
        authorService.deleteOldImg(author.getImagePath());
        author.setImagePath(authorService.uploadImg(file));
        return ResponseEntity.ok(authorService.save(author));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){
        Author author = authorService.findById(id).get();
        authorService.deleteById(id);
        authorService.deleteOldImg(author.getImagePath());
        return ResponseEntity.ok(1);
    }

    @GetMapping("/imgs/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id)  {

        try {
            String fileName = authorService.findById(id).get().getImagePath();
            Path imgPath = Paths.get(imgDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(imgPath.toUri());

            if(!resource.exists()){
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(imgPath);
            if(contentType == null){
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
