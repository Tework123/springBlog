package com.example.springBlog.dtos.user;

import com.example.springBlog.dtos.post.PostGetDto;
import com.example.springBlog.entities.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserProfilePageDto {
    private Long id;
    private String email;
    private String username;
    private List<PostGetDto> posts = new ArrayList<>();
    private LocalDate dateJoined;


    public static UserProfilePageDto toDto(User user) {
        UserProfilePageDto userProfilePageDto = new UserProfilePageDto();
        userProfilePageDto.setId(user.getId());
        userProfilePageDto.setUsername(user.getUsername());
        userProfilePageDto.setEmail(user.getEmail());
        userProfilePageDto.setDateJoined(user.getDateJoined());

        List<PostGetDto> postGetDtoList = PostGetDto.toListDto(user.getPosts());
        userProfilePageDto.setPosts(postGetDtoList);

        return userProfilePageDto;

    }

}





