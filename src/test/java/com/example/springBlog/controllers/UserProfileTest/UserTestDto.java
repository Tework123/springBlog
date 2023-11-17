package com.example.springBlog.controllers.UserProfileTest;

import com.example.springBlog.dtos.user.SignUpDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class UserTestDto {

    public static String signUpDto() throws JsonProcessingException {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("user1");
        signUpDto.setPassword("1234567");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(signUpDto);
        return requestJson;
    }

}