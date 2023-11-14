package com.example.springBlog.dtos.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostCreateDto {
    private String name;

    @NotNull
    private String text;
}
