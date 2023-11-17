package com.example.springBlog.controllers.PostTest;

import com.example.springBlog.dtos.post.PostCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class PostTestDto {

    // hard coding:
    public static String postCreateDto() throws JsonProcessingException {
        PostCreateDto postCreateDto = new PostCreateDto();
        postCreateDto.setName("post1");
        postCreateDto.setText("some text");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(postCreateDto);
        return requestJson;
    }

    public static String postEditDto() throws JsonProcessingException {
        PostCreateDto postCreateDto = new PostCreateDto();
        postCreateDto.setName("post1Edit");
        postCreateDto.setText("some text edit");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(postCreateDto);
        return requestJson;
    }
}
