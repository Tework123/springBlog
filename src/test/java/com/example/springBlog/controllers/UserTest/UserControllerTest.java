package com.example.springBlog.controllers.UserTest;


import com.example.springBlog.controllers.UserProfileTest.UserTestDto;
import com.example.springBlog.dtos.post.PostCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/applicationTest.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Sql(value = {"/clearDb.sql"})
    @Test
    public void signupTestSuccess() throws Exception {

        this.mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .content(UserTestDto.signUpDto()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("User registered successfully")));
    }

    @Test
    public void signinTestSuccess() throws Exception {

        this.mockMvc.perform(post("/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .content(UserTestDto.signUpDto()))

                .andDo(print())
                .andExpect(status().isOk());

//        отсюда забираем токен, когда старый истечет
//                .andExpect(jsonPath("$.token", is("123")));

    }

}





