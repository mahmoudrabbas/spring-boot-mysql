package com.store.service;

import com.store.base.repo.BaseRepo;
import com.store.base.service.BaseServiceImpl;
import com.store.entity.Author;
import com.store.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AuthorService extends BaseServiceImpl<Author, Long> {


    @Autowired
    private AuthorRepo authorRepo;

    @Value("${upload.base.path}")
    private String UPLOAD_DIR;

    public AuthorService(BaseRepo<Author, Long> baseRepo) {
        super(baseRepo);
    }


    public Author findByEmail(String email){
        return authorRepo.findByEmail(email);
    }

    public String uploadImg(MultipartFile file){
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            String imgName = UUID.randomUUID()+"_"+file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR+imgName);
            Files.write(path, file.getBytes());
            return path.toString();

        } catch (IOException e) {
            throw new RuntimeException("Error uploading the image: "+file.getOriginalFilename());
        }
    }


    public byte[] getImage(String imageName) {
        try {
            Path imagePath = Paths.get(UPLOAD_DIR+imageName);
            return Files.readAllBytes(imagePath);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
