package com.example.springBlog.repositories;

import com.example.springBlog.entities.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    //  ищем всех авторов, на которых подписан этот follower
    List<Follower> findByUserFollowerId(Long id);

//    List<FollowerUserAuthor> findFollowerUserAuthorByUserFollowerId(Integer id);

    List<Follower> findByUserAuthorId(Long id);

    Follower findByUserFollowerIdAndUserAuthorId(Long followerId, Long authorId);


}