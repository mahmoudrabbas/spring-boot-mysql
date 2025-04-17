package com.store.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
public class S3Service {
    @Value("${aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFileToS3(MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());


        amazonS3.putObject(
                new PutObjectRequest(bucket, key, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, key).toString();
    }

    public void deleteFileFromS3(String url){
        String key = URI.create(url).getPath().substring(1);
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
    }
}
