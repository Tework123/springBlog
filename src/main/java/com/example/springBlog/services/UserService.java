package com.example.springBlog.services;

import com.example.springBlog.dtos.User.SignUpDto;
import com.example.springBlog.entities.User;
import com.example.springBlog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean createUser(SignUpDto signUpDto) {
//        if (userRepository.existsByEmail(signUpDto.getEmail())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exist");
//        }

        User user = new User();
//        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

//        user.getRoles().add(Role.ROLE_USER);

        userRepository.save(user);
        return true;
    }
}



