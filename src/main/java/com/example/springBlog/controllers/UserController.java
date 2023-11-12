package com.example.springBlog.controllers;


import com.example.springBlog.dto.UserDto;
import com.example.springBlog.entities.UserEntity;
import com.example.springBlog.exeptions.ExampleExeption;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("")
    @JsonFormat
    public ResponseEntity getUsers() {
        System.out.println(userRepository.findAll());
        return ResponseEntity.ok("Cerver worked norm");
    }

    @PostMapping("")
    public ResponseEntity registration(@RequestBody UserEntity user) {
        userService.registration(user);
        return ResponseEntity.ok("Save user ok");
    }
// надо прочекать jekson, нужен ли он вообще, тут вроде и так норм работает,
//    через dto можно делать, но это не точно, чекаем видос


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) throws ExampleExeption {
        System.out.println(userRepository.findById(id));
        UserEntity userEntity = userRepository.findById(id).orElse(null);

        return UserDto.toDto(userEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity geleteUser(@PathVariable Long id) throws ExampleExeption {
        userRepository.deleteById(id);

        return ResponseEntity.ok("User delete success");
    }


}



