package com.example.springBlog.services;


import com.example.springBlog.entities.UserPostStatus;
import com.example.springBlog.entities.enums.PostStatus;
import com.example.springBlog.exceptions.customExceptions.CustomException;
import com.example.springBlog.dtos.post.PostCreateDto;
import com.example.springBlog.dtos.post.PostEditDto;
import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import com.example.springBlog.repositories.PostRepository;

import com.example.springBlog.repositories.UserPostStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private UserPostStatusRepository userPostStatusRepository;

    public List<Post> listPosts() {
        return postRepository.findAllByOrderByDateCreateDesc();
    }

    public Post createPost(User currentUser, PostCreateDto postCreateDto) {
        Post post = new Post();
        post.setName(postCreateDto.getName());
        post.setText(postCreateDto.getText());
        post.setUser(currentUser);
        return postRepository.save(post);

    }

    public void editPost(Long id, PostEditDto postEditDto, User currentUser) {
        Post oldPost = postRepository.findById(id).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "Post not found"));
        if (oldPost.getUser() != currentUser) {
            throw new CustomException(HttpStatus.FORBIDDEN, "You don't owner this post");
        }
        if (postEditDto.getName() != null) {
            oldPost.setName(postEditDto.getName());
        }
        if (postEditDto.getText() != null) {
            oldPost.setText(postEditDto.getText());
        }
        postRepository.save(oldPost);
    }

    public void deletePost(Long id, User currentUser) {
        Post post = postRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Post not found"));

        if (post.getUser() != currentUser) {
            throw new CustomException(HttpStatus.FORBIDDEN, "You don't owner this post");
        }
        postRepository.deleteById(id);
    }

    /*
Достаем запись о статусе поста, если она не существует
 - добавляем запись(по умолчанию лайк)
Если существует - проверяем, если стоит лайк - меняем на дизлайк и наоборот.
 */
    public PostStatus setPostStatus(Long id, User currentUser) {

        Post post = postRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Post not found"));

        UserPostStatus oldUserPostStatus = userPostStatusRepository.
                findByPostIdAndUser(id, currentUser);
        if (oldUserPostStatus == null) {

            UserPostStatus newUserPostStatus = new UserPostStatus();
            newUserPostStatus.setPost(post);
            newUserPostStatus.setUser(currentUser);
            newUserPostStatus.setPostStatus(PostStatus.LIKE);
            userPostStatusRepository.save(newUserPostStatus);

            post.setLikes(post.getLikes() + 1);
            postRepository.save(post);
            return newUserPostStatus.getPostStatus();

        } else {
            if (oldUserPostStatus.getPostStatus() == PostStatus.LIKE) {
                oldUserPostStatus.setPostStatus(PostStatus.DISLIKE);
                userPostStatusRepository.save(oldUserPostStatus);

                post.setLikes(post.getLikes() - 1);
                post.setDislikes(post.getDislikes() + 1);
                postRepository.save(post);

            } else {
                oldUserPostStatus.setPostStatus(PostStatus.LIKE);
                userPostStatusRepository.save(oldUserPostStatus);

                post.setLikes(post.getLikes() + 1);
                post.setDislikes(post.getDislikes() - 1);
                postRepository.save(post);
            }
            return oldUserPostStatus.getPostStatus();
        }
    }

    public List<Post> getLikedPosts(User currentUser) {
        List<UserPostStatus> likedUserPosts = userPostStatusRepository
                .findByUserAndPostStatus(currentUser, PostStatus.LIKE);

//      UserPostStatus -> Post
        List<Post> likedPosts = new ArrayList<>();
        for (int i = 0; i < likedUserPosts.size(); i++) {
            likedPosts.add(likedUserPosts.get(i).getPost());
        }
        likedPosts.sort(Comparator.comparing(Post::getDateCreate, Comparator.reverseOrder()));

        return likedPosts;
    }


    public void getUserPosts(User user) {
        List<Post> posts = postRepository.findByUser(user);
        for (Post post : posts) {
            post.setUser(null);
        }
    }
}




