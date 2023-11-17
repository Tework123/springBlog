package com.example.springBlog.controllers.PostTest;

import com.example.springBlog.controllers.PostController;
import com.example.springBlog.entities.Post;
import com.example.springBlog.repositories.PostRepository;
import com.example.springBlog.settings.security.jwt.JwtTokenUtil;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/applicationTest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostControllerTest {

    //      пока так, но токен когда-то должен истечь, поэтому надо будет новый вставить
    final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcwMDIyMzM1MiwiZXhwIjoxNzAxMTIzMzUyfQ.Z3tAzqTOmh3QgSWnjx05KueMUSoBrGBSR84ujrEYwiU";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PostController postController;
    @Autowired
    private PostRepository postRepository;


    @Test
    @Order(1)
    public void createPostError401() throws Exception {

        this.mockMvc.perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8").content(PostTestDto
                                .postCreateDto())).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(2)
    public void createPost200() throws Exception {
        this.mockMvc.perform(post("/post")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .content(PostTestDto.postCreateDto()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Post create post1")));
    }

    @Test
    @Order(3)
    public void postsTest() throws Exception {
        this.mockMvc.perform(get("/post"))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, World")));
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @Order(4)
    public void postTest() throws Exception {
        List<Post> posts = postRepository.findAll();

        this.mockMvc.perform(get("/post/" + posts.get(0).getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("post1")));
    }

    @Test
    @Order(5)
    public void editPostTestError401() throws Exception {
        List<Post> posts = postRepository.findAll();
        this.mockMvc.perform(patch("/post/" + posts.get(0).getId()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(6)
    public void editPostTest200() throws Exception {
        List<Post> posts = postRepository.findAll();
        this.mockMvc.perform(patch("/post/" + posts.get(0).getId())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .content(PostTestDto.postEditDto()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    public void editPostTest200Response() throws Exception {
        List<Post> posts = postRepository.findAll();

        this.mockMvc.perform(get("/post/" + posts.get(0).getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("post1Edit")))
                .andExpect(jsonPath("$.text", is("some text edit")));
    }

//    тесты на:
//    редактирование и удаление поста(нужно еще на owner проверить, тогда нужен второй юзер)
//    статусы постов, проверить лайк и дизлайк
}