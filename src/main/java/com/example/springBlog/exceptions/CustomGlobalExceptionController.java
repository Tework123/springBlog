package com.example.springBlog.exceptions;


import com.example.springBlog.exceptions.customExceptions.CustomException;
import com.example.springBlog.exceptions.customExceptions.NoOwnerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


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

    //    сделать так, чтобы все ошибки обрабатывались как сверху, а то тут не очень,
//    но статус тоже
//    далее делаем дальше проект, раскапывает dto, надо их как то красиво имплементировать
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(CustomException ex) {

        return new ResponseEntity<>(ex.getErrorMessage(), ex.getHttpStatus());
    }


}



