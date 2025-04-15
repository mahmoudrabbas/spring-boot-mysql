package com.store.service;

import com.store.entity.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileUploadService {

    @Autowired
    private AuthorService authorService;


    private Path storageLocation;
    @Value("${file.upload.base-path}")
    private String basePath;

    Logger log = LoggerFactory.getLogger(FileUploadService.class);



    public void updateImagePath(Long id, String pathType, String imageName){
        if(pathType.contains("authors")){
            Author author = authorService.findById(id).get();
            author.setImagePath(pathType+"/"+imageName);
            authorService.save(author);
        }
    }

    public String storeFile(File file, Long id, String pathType){

        this.storageLocation = Paths.get(basePath+pathType).toAbsolutePath().normalize(); // folder name kamel

        try {
            Files.createDirectories(this.storageLocation); // create the folder
        }catch (Exception ex){ // if error happen while creating that folder
            log.error("Failed to create Directory: {}",ex.getMessage());
        }


        String fileName = StringUtils.cleanPath(id+"_"+file.getName()); // file name

        try {
            if(fileName.contains("..")){
                log.error("invalid file name: {}", fileName);
            }

            Path targetLocation = this.storageLocation.resolve(fileName);
            InputStream inputStream = new FileInputStream(file);
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            updateImagePath(id, pathType, fileName);

            System.out.println(this.storageLocation+fileName);
            return fileName;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public File convertMultiPartToFile(final MultipartFile multipartFile){
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try (final FileOutputStream fos = new FileOutputStream(file)){
            fos.write(multipartFile.getBytes());
        }catch (IOException ex){
            log.error("Error Converting Multipart file to file: {}", ex.getMessage());
        }

        return file;
    }





}
