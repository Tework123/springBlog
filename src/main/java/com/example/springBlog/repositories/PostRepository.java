package com.example.springBlog.repositories;

import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByOrderByDateCreateDesc();

    public List<Post> findByUser(User user);

}
