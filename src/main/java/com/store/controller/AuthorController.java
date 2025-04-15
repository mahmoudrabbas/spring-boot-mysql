package com.store.controller;

import com.store.entity.Author;
import com.store.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

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

    @PutMapping("")
    public ResponseEntity<?> updateOne(@RequestBody @Valid Author entity){
        Author author = authorService.findById(entity.getId()).orElseThrow();
        author.setFullName(entity.getFullName());
        author.setEmail(entity.getEmail());

        return ResponseEntity.ok(authorService.save(author));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){
        authorService.deleteById(id);
        return ResponseEntity.ok(1);
    }

    @GetMapping("/imgs/{imgName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imgName){
        byte [] img = authorService.getImage(imgName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(img);
    }

}
