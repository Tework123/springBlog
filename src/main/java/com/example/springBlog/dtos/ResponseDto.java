package com.example.springBlog.dtos;

import lombok.Data;

@Data
public class ResponseDto {
    private String message;

    public static ResponseDto toDto(String message) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(message);
        return responseDto;
    }
}
