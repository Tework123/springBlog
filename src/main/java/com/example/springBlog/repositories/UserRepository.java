package com.example.springBlog.repositories;

import com.example.springBlog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findById(Integer id);

    Boolean existsByEmail(String email);

}
