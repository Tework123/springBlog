package com.example.springBlog.dtos;


import com.example.springBlog.entities.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String name;

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
//        userDto.setName(user.getName());
        return userDto;

    }

}







