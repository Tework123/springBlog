package com.example.springBlog.controllers.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackageClasses = CustomException.class)
public class ErrorController {
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    ResponseEntity<?> handleException(CustomException ex,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

        return new ResponseEntity<>(ex.getErrorMessage(), ex.getHttpStatus());
    }
}
