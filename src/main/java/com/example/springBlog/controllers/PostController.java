package com.example.springBlog.controllers;

import com.example.springBlog.dtos.UserDto;
import com.example.springBlog.dtos.post.*;
import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import com.example.springBlog.repositories.PostRepository;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.PostService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    private UserRepository userRepository;
    private PostRepository postRepository;

    @GetMapping("")
    public List<PostGetDto> getPosts() {
        List<Post> posts = postService.listPosts();
        return PostsGetDto.toDto(posts);
    }

    @GetMapping("/{id}")
    public PostGetDto getPost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return PostGetDto.toDto(post);
    }

    @PostMapping("")
    public ResponseEntity<String> createPost(@Validated @RequestBody PostCreateDto postCreateDto,
                                             Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName());

        Post savedPost = postService.createPost(currentUser, postCreateDto);
//        postService.savePhotos(file1, file2, savedPost, currentUser);
        return ResponseEntity.ok("Post create " + savedPost.getName());

    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> editPost(@PathVariable("id") Long id,
                                           @Validated
                                           @RequestBody PostEditDto postEditDto,
                                           Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName());

        postService.editPost(id, postEditDto, currentUser);
        return ResponseEntity.ok("Post edit");
    }

}





