package com.example.springBlog.dtos.post;

import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostGetDto {
    private String name;
    private String text;
    private LocalDateTime dateCreate;
    private User user;


    public static PostGetDto toDto(Post post) {
        PostGetDto postGetDto = new PostGetDto();
        postGetDto.setName(post.getName());
        postGetDto.setText(post.getText());
        postGetDto.setDateCreate(post.getDateCreate());
//        postGetDto.setUser(post.getUser().getId());
//        посмотреть как создавать вложенные dto
//        далее разобраться с валидацией полей на вход
        return postGetDto;
    }

}
