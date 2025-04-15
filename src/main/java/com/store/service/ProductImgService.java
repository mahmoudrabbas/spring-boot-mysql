package com.store.service;


import com.store.entity.ProductImage;
import com.store.repository.ProductImgRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ProductImgService {
    @Autowired
    private ProductImgRepo productImgRepo;

    public ProductImage insert(ProductImage productImage){
        return productImgRepo.save(productImage);
    }

    public ProductImage update(MultipartFile file){
        try {
            String uuid = UUID.randomUUID().toString();
            ProductImage img = new ProductImage();
            img.setImageName(uuid + file.getOriginalFilename());
            img.setImageType(file.getContentType());
            img.setImage(file.getBytes());
            return productImgRepo.save(img);

        }catch (IOException ex){
            throw new RuntimeException("Error Saving the image");
        }
    }


    public ProductImage getImage(Long id){
        return productImgRepo.findById(id).orElseThrow();
    }
}
