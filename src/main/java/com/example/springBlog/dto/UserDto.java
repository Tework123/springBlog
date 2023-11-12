package com.example.springBlog.dto;


import com.example.springBlog.entities.UserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String userName;

    public static UserDto toDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setUserName(userEntity.getUserName());
        return userDto;

    }

}







