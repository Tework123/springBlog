package com.example.springBlog.controllers;

import com.example.springBlog.dtos.UserDto;
import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.PostService;
import com.example.springBlog.settings.security.jwt.JwtRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    private UserRepository userRepository;

    @GetMapping("")
    public List<Post> getPosts(Authentication auth) {
        System.out.println(auth);
        List<Post> posts = postService.listPosts();
        System.out.println(posts);
        return posts;
    }

    @PostMapping("")
    public ResponseEntity<String> createPost(@RequestBody Post post, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName());
        System.out.println(currentUser);
        System.out.println(post);

        Post savedPost = postService.createPost(currentUser, post);
//        postService.savePhotos(file1, file2, savedPost, currentUser);
        return ResponseEntity.ok("Post create");

    }
// надо понять, почему он такую портянку кидает в ответ с ошибкой, может в настройках
//    что то стоит не то, далее по проекту идет, дтошки только надо добавлять, какие надо

}





