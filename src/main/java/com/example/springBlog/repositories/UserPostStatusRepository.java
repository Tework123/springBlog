package com.example.springBlog.repositories;

import com.example.springBlog.entities.User;
import com.example.springBlog.entities.UserPostStatus;
import com.example.springBlog.entities.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPostStatusRepository extends JpaRepository<UserPostStatus, Long> {
    UserPostStatus findByPostIdAndUser(Long id, User currentuser);

    List<UserPostStatus> findByUserAndPostStatus(User currentUser, PostStatus postStatus);


}