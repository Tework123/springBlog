package com.example.springBlog.controllers.user;

import com.example.springBlog.dtos.UserDto;
import com.example.springBlog.entities.User;
import com.example.springBlog.exceptions.ExampleExeption;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class UserProfileController {

    private final UserRepository userRepository;
    private final UserService userService;



    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) throws ExampleExeption {
        System.out.println(userRepository.findById(id));
        User user = userRepository.findById(id).orElse(null);

        return UserDto.toDto(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity geleteUser(@PathVariable Long id) throws ExampleExeption {
        userRepository.deleteById(id);

        return ResponseEntity.ok("User delete success");
    }
}
