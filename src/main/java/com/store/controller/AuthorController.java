package com.store.controller;

import com.store.entity.Author;
import com.store.service.AuthorService;
import com.store.service.CloudinaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CloudinaryService cloudinaryService;

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
        try {
            author.setImagePath(cloudinaryService.uploadFile(file.getBytes(), file.getOriginalFilename()));
            author.setPublicId(file.getOriginalFilename());
            return ResponseEntity.ok(authorService.save(author));

        }catch (IOException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateOne(@RequestPart("author") @Valid Author entity, @RequestPart("file") MultipartFile file) {
        try {
            Author author = authorService.findById(entity.getId()).orElseThrow();
            author.setFullName(entity.getFullName());
            author.setEmail(entity.getEmail());
//      authorService.deleteOldImg(author.getImagePath());
            cloudinaryService.deleteFile(author.getPublicId());
//            author.setImagePath(authorService.uploadImg(file));
            author.setImagePath(cloudinaryService.uploadFile(file.getBytes(), file.getOriginalFilename()));
            author.setPublicId(file.getOriginalFilename());
            return ResponseEntity.ok(authorService.save(author));
        } catch (IOException ex) {
            return ResponseEntity.badRequest().body("Error updating the image");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){
        Author author = authorService.findById(id).get();
        authorService.deleteById(id);
        try {
            String done = cloudinaryService.deleteFile(author.getPublicId());
            return ResponseEntity.ok().body("Deleted");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting the image");
        }
//        authorService.deleteOldImg(author.getImagePath());
//        return ResponseEntity.ok(1);
    }

    @PostMapping("/to-cdn")
    public ResponseEntity<?> uploadImgToCDN(@RequestParam MultipartFile file){
        try {
            String url = cloudinaryService.uploadFile(file.getBytes(), file.getOriginalFilename());
            return ResponseEntity.ok().body(url);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
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
            System.out.println(contentType);
            if(contentType == null){
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @DeleteMapping("/delete-cdn/{public_id}")
//    public ResponseEntity<?> deleteFromDdn(@PathVariable String public_id){
//        try {
//            String done = cloudinaryService.deleteFile(public_id);
//            return ResponseEntity.ok().body("Deleted");
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Error deleting the image");
//        }
//    }

}
