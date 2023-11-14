package com.example.springBlog.controllers.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorMessage;
}