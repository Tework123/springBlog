package com.example.springBlog.controllers;

import com.example.springBlog.entities.User;
import com.example.springBlog.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping("/admin")
    public ResponseEntity<?> admin(Authentication auth) {

        System.out.println(auth.getName());
        System.out.println(auth);
        User user = userRepository.findByUsername(auth.getName());


        System.out.println("admin here");
        return new ResponseEntity<>("admin here", HttpStatus.OK);
    }


    @GetMapping("/user")
    public List<User> getUsers() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }
}
