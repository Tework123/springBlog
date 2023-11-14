package com.example.springBlog.services;


import com.example.springBlog.dtos.post.PostCreateDto;
import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import com.example.springBlog.repositories.PostRepository;

import lombok.AllArgsConstructor;
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

}
