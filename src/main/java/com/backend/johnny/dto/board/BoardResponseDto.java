package com.backend.johnny.dto.board;

import com.backend.johnny.entity.Board;
import lombok.Getter;
import lombok.Setter;

public class BoardResponseDto {

    @Getter
    @Setter
    public static class FindBoard {
        private Long boardId;
        private String title;
        private String content;

        public FindBoard(Board board) {
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }
}
