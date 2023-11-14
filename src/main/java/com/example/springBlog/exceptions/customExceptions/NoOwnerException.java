package com.example.springBlog.exceptions.customExceptions;

import org.springframework.http.HttpStatus;

public class NoOwnerException extends CustomException {

    public NoOwnerException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
