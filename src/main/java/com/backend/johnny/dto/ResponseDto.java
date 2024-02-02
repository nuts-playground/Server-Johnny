package com.backend.johnny.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ResponseDto {

    private String timestamp;
    private Object data;


    public ResponseDto(Object data) {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.data = data;
    }
}
