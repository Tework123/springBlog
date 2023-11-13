package com.example.springBlog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ErrorController {

    @GetMapping("/error/forbidden")
    public ResponseEntity<?> forbidden() {
        return new ResponseEntity<>("sorry forbidden", HttpStatus.FORBIDDEN);
    }

}
