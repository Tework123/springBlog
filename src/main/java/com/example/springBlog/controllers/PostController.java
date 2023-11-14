package com.example.springBlog.controllers;

import com.example.springBlog.dtos.UserDto;
import com.example.springBlog.dtos.post.PostCreateDto;
import com.example.springBlog.dtos.post.PostGetDto;
import com.example.springBlog.dtos.post.Views;
import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import com.example.springBlog.repositories.PostRepository;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.PostService;
import com.fasterxml.jackson.annotation.JsonView;
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
    public List<Post> getPosts(Authentication auth) {
        System.out.println(auth);
        List<Post> posts = postService.listPosts();
        System.out.println(posts);
        return posts;
    }

    @GetMapping("/{id}")
    public PostGetDto getPost(@PathVariable Long id, Authentication auth) {
        System.out.println(auth);
        Post post = postRepository.findById(id).orElse(null);
        System.out.println(post);
        return PostGetDto.toDto(post);
    }

    @PostMapping("")
    public ResponseEntity<String> createPost(@Validated @RequestBody PostCreateDto postCreateDto,
                                             Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName());
        System.out.println(currentUser);
        System.out.println(postCreateDto);

        Post savedPost = postService.createPost(currentUser, postCreateDto);
//        postService.savePhotos(file1, file2, savedPost, currentUser);
        return ResponseEntity.ok("Post create");

    }

}





