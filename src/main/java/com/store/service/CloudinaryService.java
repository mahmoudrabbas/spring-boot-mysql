package com.store.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;
    public CloudinaryService(@Value("${cloudinary.cloud-name}") String cloudName,
                             @Value("${cloudinary.api-key}") String apiKey,
                             @Value("${cloudinary.api-secret}") String apiSecret){

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name",cloudName,
                "api_key",apiKey,
                "api_secret",apiSecret
        ));
    }


    public String uploadFile(byte[] file, String fileName) throws IOException {
        Map mapResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", fileName));
        return mapResult.get("secure_url").toString();
    }


    public String deleteFile(String public_id) throws IOException{
        Map mapResult = cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
        return mapResult.get("result").toString();
    }
}
