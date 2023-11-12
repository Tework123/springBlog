package com.example.springBlog.repositories;

import com.example.springBlog.entities.UserEntity;
import com.example.springBlog.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
