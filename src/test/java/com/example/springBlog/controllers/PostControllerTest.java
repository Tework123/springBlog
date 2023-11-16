package com.example.springBlog.controllers;

import com.example.springBlog.entities.Post;
import com.example.springBlog.repositories.UserRepository;
import com.example.springBlog.services.PostService;
import com.example.springBlog.settings.security.JwtUserDetailsService;
import com.example.springBlog.settings.security.WebSecurityConfig;
import com.example.springBlog.settings.security.jwt.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration(classes={Application.class, MvcConfig.class, SecurityConfig.class})
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;

    public PostControllerTest(JwtTokenUtil jwtTokenUtil, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        jwtUserDetailsService.loadUserByUsername("asd");

//        нужна отдельная функция для генерации токена, хз как
    }

    @Autowired
    private PostController postController;

    @Test
    public void test() throws Exception {
        assertThat(postController).isNotNull();

    }

    @Test
    public void test2() throws Exception {
        this.mockMvc.perform(get("/post"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));

    }

    @Test
    public void test3() throws Exception {

        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername("hello");
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println(token);

        this.mockMvc.perform(post("/post").header("Authorization", token)
                        .param("name", "1").param("text", "2"))
                .andDo(print())
                .andExpect(status().isBadRequest())
//                .andExpect(content().json("{'message': 'asd'}"));
                .andExpect(jsonPath("$.message", is("ge")));
    }


}