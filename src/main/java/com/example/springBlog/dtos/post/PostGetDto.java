package com.example.springBlog.dtos.post;

import com.example.springBlog.dtos.user.UserGetDto;
import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostGetDto {
    private String name;
    private String text;
    private LocalDateTime dateCreate;
    private UserGetDto user;


    public static PostGetDto toDto(Post post) {
        PostGetDto postGetDto = new PostGetDto();
        postGetDto.setName(post.getName());
        postGetDto.setText(post.getText());
        postGetDto.setDateCreate(post.getDateCreate());

        UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId(post.getUser().getId());
        userGetDto.setUsername(post.getUser().getUsername());
        postGetDto.setUser(userGetDto);

        return postGetDto;
    }

}
