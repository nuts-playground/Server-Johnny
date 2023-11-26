package com.backend.johnny.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardRequestDto {

    @Getter
    @Setter
    public static class InsertDto {
        private String title;
        private String content;
    }

}
