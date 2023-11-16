package com.example.springBlog.dtos.admin;

import com.example.springBlog.dtos.post.PostGetDto;
import com.example.springBlog.dtos.user.UserProfilePageDto;
import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.User;
import com.example.springBlog.entities.enums.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class AdminGetUsersDto {
    private Long id;
    private String username;
    private String email;
    private boolean active;
    private Set<Role> roles;
    private LocalDate dateJoined;

    public static AdminGetUsersDto toDto(User user) {
        AdminGetUsersDto adminGetUsersDto = new AdminGetUsersDto();
        adminGetUsersDto.setId(user.getId());
        adminGetUsersDto.setUsername(user.getUsername());
        adminGetUsersDto.setEmail(user.getEmail());
        adminGetUsersDto.setDateJoined(user.getDateJoined());
        adminGetUsersDto.setActive(user.isActive());
        adminGetUsersDto.setRoles(user.getRoles());

        return adminGetUsersDto;

    }

    public static List<AdminGetUsersDto> toListDto(List<User> usersFromDb) {
        List<AdminGetUsersDto> users = usersFromDb.stream().map(AdminGetUsersDto::toDto)
                .collect(Collectors.toList());
        return users;

    }

}







