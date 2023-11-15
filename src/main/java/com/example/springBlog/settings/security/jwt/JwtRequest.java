package com.example.springBlog.settings.security.jwt;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class JwtRequest implements Serializable {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
