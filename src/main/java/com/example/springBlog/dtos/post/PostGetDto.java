package com.example.springBlog.dtos.post;

import com.example.springBlog.dtos.user.UserGetDto;
import com.example.springBlog.entities.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostGetDto {
    private String name;
    private String text;
    private LocalDateTime dateCreate;
    private UserGetDto user;
    private Integer likes;
    public Integer dislikes;


    public static PostGetDto toDto(Post post) {
        PostGetDto postGetDto = new PostGetDto();
        postGetDto.setName(post.getName());
        postGetDto.setText(post.getText());
        postGetDto.setDateCreate(post.getDateCreate());
        postGetDto.setLikes(post.getLikes());
        postGetDto.setDislikes(post.getDislikes());

        if (post.getUser() != null) {
            UserGetDto userGetDto = new UserGetDto();
            userGetDto.setId(post.getUser().getId());
            userGetDto.setUsername(post.getUser().getUsername());
            postGetDto.setUser(userGetDto);
        }

        return postGetDto;
    }

    public static List<PostGetDto> toListDto(List<Post> postsFromDb) {
        List<PostGetDto> posts = postsFromDb.stream().map(PostGetDto::toDto)
                .collect(Collectors.toList());
        return posts;

    }

}
