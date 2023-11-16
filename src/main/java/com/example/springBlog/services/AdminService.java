package com.example.springBlog.services;

import com.example.springBlog.entities.User;
import com.example.springBlog.entities.enums.Role;
import com.example.springBlog.exceptions.customExceptions.CustomException;
import com.example.springBlog.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AdminService {

    private UserRepository userRepository;

    public boolean userActivate(Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));

        user.setActive(!user.isActive());
        userRepository.save(user);
        return user.isActive();
    }

    public Set<Role> userChangeRole(Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getRoles().contains(Role.ROLE_USER)) {
            user.getRoles().clear();
            user.getRoles().add(Role.ROLE_ADMIN);
        } else {
            user.getRoles().clear();
            user.getRoles().add(Role.ROLE_USER);
        }
        userRepository.save(user);
        return user.getRoles();

    }

}






