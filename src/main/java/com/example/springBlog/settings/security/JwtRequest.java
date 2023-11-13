package com.example.springBlog.settings.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class JwtRequest implements Serializable {
    private String username;
    private String password;
}
