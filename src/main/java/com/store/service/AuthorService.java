package com.store.service;

import com.store.base.repo.BaseRepo;
import com.store.base.service.BaseServiceImpl;
import com.store.entity.Author;
import com.store.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class AuthorService extends BaseServiceImpl<Author, Long> {

    @Value("${file.upload.base-path}")
    private String fileStorageLocation;


    @Autowired
    private AuthorRepo authorRepo;

    public AuthorService(BaseRepo<Author, Long> baseRepo) {
        super(baseRepo);
    }

    public Author findByEmail(String email){
        return authorRepo.findByEmail(email);
    }


    // upload to folder on the system server
    public String uploadImg(MultipartFile file){
        try {
        Path uploadPath = Paths.get(fileStorageLocation);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String imageName = UUID.randomUUID()+extension;

        Path filePath = uploadPath.resolve(imageName).normalize();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return imageName;
        } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage());
        }

    }


    // from the folder on system server
    public void deleteOldImg(String imgPath){
        Path filePath = Paths.get(fileStorageLocation).resolve(imgPath).normalize();
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
