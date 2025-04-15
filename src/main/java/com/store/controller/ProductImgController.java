package com.store.controller;

import com.store.entity.ProductImage;
import com.store.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/img")
public class ProductImgController {

    @Autowired
    private ProductImgService productImgService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadImg(@RequestParam MultipartFile file){
        ProductImage saved = productImgService.update(file);
        return ResponseEntity.status(200).body("Image uploaded with ID: " + saved.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImg(@PathVariable Long id){
        ProductImage img = productImgService.getImage(id);
        if(img == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(img.getImageType()))
                .body(img.getImage());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductImage> getProductImage(@PathVariable Long id){
        ProductImage productImage = productImgService.getImage(id);
        if(productImage == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(productImage);
    }



}
