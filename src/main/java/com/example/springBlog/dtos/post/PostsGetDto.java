package com.example.springBlog.dtos.post;

import com.example.springBlog.dtos.user.UserGetDto;
import com.example.springBlog.entities.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostsGetDto {
    private String name;
    private String text;

    private List<PostGetDto> posts;


    public static List<PostGetDto> toDto(List<Post> postsFromDb) {

//        for (int i = 0; i < postsFromDb.size(); i++) {
//            PostGetDto postGetDto = new PostGetDto();
//
//            Post postFromDb = postsFromDb.get(i);
//
//            postGetDto.setText(postFromDb.getText());
//            postGetDto.setName(postFromDb.getName());
//            postGetDto.setDateCreate(postFromDb.getDateCreate());
//
//            UserGetDto userGetDto = new UserGetDto();
//            userGetDto.setId(postFromDb.getUser().getId());
//            userGetDto.setUsername(postFromDb.getUser().getUsername());
//
//            postGetDto.setUser(userGetDto);
//
//            posts.add(postGetDto);
//
//        }

        List<PostGetDto> posts = postsFromDb.stream().map(PostGetDto::toDto)
                .collect(Collectors.toList());
        System.out.println(posts);
        return posts;

    }
}
