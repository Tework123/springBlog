package com.example.springBlog.dtos.user;

import com.example.springBlog.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserEditDto {

    @NotNull
    private String email;

}
