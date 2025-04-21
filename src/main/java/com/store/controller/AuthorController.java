package com.store.controller;

import com.store.entity.Author;
import com.store.service.AuthorService;
import com.store.service.CloudinaryService;
import com.store.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Autowired
    private S3Service s3Service;

    @Value("${file.upload.base-path}")
    private String imgDir;

    @Operation(summary = "get all authors", responses = {
            @ApiResponse(responseCode = "200", description = "return all users"),
    })
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(authorService.findAll());
    }
    @Operation(summary = "get author by id", responses = {
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "404", description = "Author Not found")

    })


    @GetMapping("{id}")
    public ResponseEntity<?> getById(@Parameter(name = "author id", example = "5") @PathVariable Long id){
        return ResponseEntity.ok(authorService.findById(id));
    }


    @Operation(summary = "add new author by sending the author data and image")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addOne(@RequestPart("author") @Valid Author author, @RequestPart("file") MultipartFile file){
        try {
            author.setImagePath(s3Service.uploadFileToS3(file));
            return ResponseEntity.ok(authorService.save(author));

        }catch (IOException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "update author by giving the author data and image")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateOne(@RequestPart("author") @Valid Author entity, @RequestPart("file") MultipartFile file) {
        try {
            Author author = authorService.findById(entity.getId()).orElseThrow();
            author.setFullName(entity.getFullName());
            author.setEmail(entity.getEmail());
            s3Service.deleteFileFromS3(author.getImagePath());
            author.setImagePath(s3Service.uploadFileToS3(file));
//      authorService.deleteOldImg(author.getImagePath());
//            cloudinaryService.deleteFile(author.getPublicId());
//            author.setImagePath(authorService.uploadImg(file));
//            author.setImagePath(cloudinaryService.uploadFile(file.getBytes(), file.getOriginalFilename()));
//            author.setPublicId(file.getOriginalFilename());
            return ResponseEntity.ok(authorService.save(author));
        } catch (IOException ex) {
            return ResponseEntity.badRequest().body("Error updating the image");
        }
    }

    @Operation(summary = "delete author by id")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@Parameter(example = "20") @PathVariable Long id){
        Author author = authorService.findById(id).orElseThrow();
        authorService.deleteById(id);
        if(!author.getImagePath().isEmpty()){
            s3Service.deleteFileFromS3(author.getImagePath());
        }
        return ResponseEntity.ok().body("Deleted");
        //        authorService.deleteOldImg(author.getImagePath());
//        return ResponseEntity.ok(1);
    }


//    @Operation(summary = "adding images to 'Cloudinary' cdn author by id")
//    @PostMapping("/to-cdn")
//    public ResponseEntity<?> uploadImgToCDN(@RequestParam MultipartFile file){
//        try {
//            String url = cloudinaryService.uploadFile(file.getBytes(), file.getOriginalFilename());
//            return ResponseEntity.ok().body(url);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }



    @Operation(summary = "get the image by the author id")
    @GetMapping("/imgs/{id}")
    public ResponseEntity<Resource> getImage(@Parameter(name = "author id", example = "5") @PathVariable Long id)  {

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
