package com.example.springBlog.services;

import com.example.springBlog.dtos.user.SignUpDto;
import com.example.springBlog.entities.User;
import com.example.springBlog.entities.enums.Role;
import com.example.springBlog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        user.setUsername(signUpDto.getUsername());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        user.getRoles().add(Role.ROLE_USER);

        userRepository.save(user);
        return true;
    }
}



