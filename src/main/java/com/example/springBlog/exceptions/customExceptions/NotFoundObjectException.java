package com.example.springBlog.exceptions.customExceptions;

import org.springframework.http.HttpStatus;

public class NotFoundObjectException extends CustomException {
    public NotFoundObjectException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }

}
