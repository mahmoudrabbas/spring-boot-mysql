package com.store.service;

import com.store.base.repo.BaseRepo;
import com.store.base.service.BaseServiceImpl;
import com.store.entity.Author;
import com.store.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService extends BaseServiceImpl<Author, Long> {

    @Value("${file.upload.base-path}")
    private String fileStorageLocation;


    private final AuthorRepo authorRepo;

    public AuthorService(BaseRepo<Author, Long> baseRepo, AuthorRepo authorRepo) {
        super(baseRepo);
        this.authorRepo = authorRepo;
    }

    @CacheEvict(value = "authorsList", allEntries = true)
    public Author save(Author entity) {
        return authorRepo.save(entity);
    }

    @Override
    @Cacheable(value = "authors", key = "#id")
    public Optional<Author> findById(Long id) {
        return Optional.of(authorRepo.findById(id).get());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "authors", key = "#id"),
            @CacheEvict(value = {"authorsList","authorsEmails"}, allEntries = true)
    })
    public void deleteById(Long id) {
        authorRepo.deleteById(id);
    }


    @Override
    @Cacheable("authorsList")
    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "authors", key = "#entity.id")
            },
            evict = {
                    @CacheEvict(value = {"authorsList","authorsEmails"}, allEntries = true)
            }
    )

    public Author update(Author entity) {
        Author author = findById(entity.getId()).get();

        author.setFullName(entity.getFullName());
        author.setEmail(entity.getEmail());
        author.setImagePath(entity.getImagePath());

        return save(author);
    }


    @Cacheable(value = "authorsEmails", key = "#email")
    public Author findByEmail(String email){
        return authorRepo.findByEmail(email);
    }


    @CacheEvict(value = {"authorsList", "authorsEmails", "authors"}, allEntries = true)
    public void cacheClear(){

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
