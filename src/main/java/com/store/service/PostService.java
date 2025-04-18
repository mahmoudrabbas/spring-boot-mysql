package com.store.service;

import com.store.entity.PostDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PostService {
    public static String BASIC_URL = "https://jsonplaceholder.typicode.com/posts";
    RestTemplate restTemplate = new RestTemplate();

    public List<PostDTO> getAllPosts(){
        System.out.println("it must work before the @After advice in detailsAspect");
        ResponseEntity<List> result = restTemplate.getForEntity(BASIC_URL, List.class);
        return result.getBody();
    }

    public PostDTO getPostById(Long id){
        ResponseEntity<PostDTO> result = restTemplate.getForEntity(BASIC_URL+"/"+id, PostDTO.class);
        return result.getBody();
    }

    public PostDTO addPost(PostDTO postDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept","application/json");
        headers.add("accept-language","en");

        HttpEntity<PostDTO> request = new HttpEntity<>(postDTO, headers);
        ResponseEntity<PostDTO> result = restTemplate.postForEntity(BASIC_URL, request, PostDTO.class);
        return result.getBody();
    }


    public void updatePost(PostDTO postDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept","application/json");
        headers.add("accept-language","en");

        HttpEntity<PostDTO> request = new HttpEntity<>(postDTO, headers);
        restTemplate.put(BASIC_URL+"/"+postDTO.getId(), request);
    }

    public void deletePost(Long id){
        restTemplate.delete(BASIC_URL+"/"+id);
    }
}
