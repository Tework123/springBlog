package com.example.springBlog.services;

import com.example.springBlog.entities.UserEntity;
import com.example.springBlog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registration(UserEntity user) {
        userRepository.save(user);

    }
}
