package com.example.springBlog.exceptions;


import com.example.springBlog.exceptions.customExceptions.CustomException;
import com.example.springBlog.exceptions.customExceptions.NoOwnerException;
import com.example.springBlog.exceptions.customExceptions.NotFoundObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoOwnerException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoOwnerException(NoOwnerException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

//    хз почему не работает
    @ExceptionHandler(NotFoundObjectException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
    public ResponseEntity<?> handleNotFoundObjectException(NotFoundObjectException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(CustomException ex) {

        return new ResponseEntity<>(ex.getErrorMessage(), ex.getHttpStatus());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> Ex(
//            Exception ex) {
//        Map<String, String> errors = new HashMap<>();
//        System.out.println(ex);
//        System.out.println(ex.getMessage());
//        System.out.println(ex.fillInStackTrace());
//        System.out.println(ex.getCause());
//        System.out.println(ex.getLocalizedMessage());
//        System.out.println(Arrays.toString(ex.getSuppressed()));
//
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }


}



