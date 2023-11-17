package com.example.springBlog.controllers;

import com.example.springBlog.dtos.post.PostCreateDto;
import com.example.springBlog.settings.security.jwt.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/applicationTest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private JwtTokenUtil jwtTokenUtil;

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

    public String createPostT() throws JsonProcessingException {
        PostCreateDto postCreateDto = new PostCreateDto();
        postCreateDto.setName("asd");
        postCreateDto.setText("123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(postCreateDto);
        return requestJson;
    }


    @Test
    public void test34() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMyIsImlhdCI6MTcwMDE4OTg4MiwiZXhwIjoxNzAxMDg5ODgyfQ.QlIiHWzeh38dmaG1KsVg_QQyyRfD8lBhIvlid--Tmko";

        this.mockMvc.perform(post("/post").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .content(createPostT()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Post create savedPost.getName()")));
    }

    @Test
    public void test3() throws Exception {

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMyIsImlhdCI6MTcwMDE4OTg4MiwiZXhwIjoxNzAxMDg5ODgyfQ.QlIiHWzeh38dmaG1KsVg_QQyyRfD8lBhIvlid--Tmko";

        this.mockMvc.perform(post("/post").header("Authorization", token)
                        .param("name", "1").contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
//                .andExpect(content().json("{'message': 'asd'}"));
                .andExpect(jsonPath("$.message", is("ge")));
    }


}