package com.example.springBlog.dtos.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserEditDto {

    @NotNull
    private String email;

}
