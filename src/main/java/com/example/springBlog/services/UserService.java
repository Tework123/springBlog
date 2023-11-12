package com.example.springBlog.services;

import com.example.springBlog.entities.User;
import com.example.springBlog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
//          перенаправляет на error.html
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exist");
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        return true;
    }
}



