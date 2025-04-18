package com.store.controller;

import com.store.entity.PostDTO;
import com.store.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody PostDTO postDTO){
        return ResponseEntity.ok().body(postService.addPost(postDTO));

    }

    @PutMapping("")
    public ResponseEntity<?> updatePost(@RequestBody PostDTO postDTO){
        postService.updatePost(postDTO);
        return ResponseEntity.ok().body(postDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok().body("Post with id "+id+" successfully deleted");
    }



}
