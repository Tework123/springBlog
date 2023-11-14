package com.example.springBlog.services;


import com.example.springBlog.exceptions.customExceptions.NoOwnerException;
import com.example.springBlog.dtos.post.PostCreateDto;
import com.example.springBlog.dtos.post.PostEditDto;
import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import com.example.springBlog.repositories.PostRepository;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;


    public List<Post> listPosts() {
        return postRepository.findAllByOrderByDateCreateDesc();
    }

    public Post createPost(User currentUser, PostCreateDto postCreateDto) {
        Post post = new Post();
        post.setName(postCreateDto.getName());
        post.setText(postCreateDto.getText());
        post.setUser(currentUser);
//        Photo photo1 = photoService.toImageEntity(file1, currentUser);
//        Photo photo2 = photoService.toImageEntity(file2, currentUser);
//        post.addPhotoToPost(photo1);
//        post.addPhotoToPost(photo2);
        return postRepository.save(post);

    }

    public void editPost(Long id, PostEditDto postEditDto, User currentUser) {

        Post oldPost = postRepository.findById(id).orElse(null);
        if (oldPost.getUser().getId() != currentUser.getId()) {
            throw new NoOwnerException(HttpStatus.FORBIDDEN, "You don't owner this post");
        }
        if (postEditDto.getName() != null) {
            oldPost.setName(postEditDto.getName());
        }
        if (postEditDto.getText() != null) {
            oldPost.setText(postEditDto.getText());
        }
        postRepository.save(oldPost);
    }

}
