package com.example.springBlog.repositories;

import com.example.springBlog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    public List<Post> findAllByOrderByDateCreateDesc();

}
