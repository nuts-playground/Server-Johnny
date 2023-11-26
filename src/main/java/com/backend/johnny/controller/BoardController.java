package com.backend.johnny.controller;

import com.backend.johnny.dto.board.BoardRequestDto;
import com.backend.johnny.dto.board.BoardResponseDto;
import com.backend.johnny.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public Map<String, Long> insertBoard(@RequestBody BoardRequestDto.InsertDto dto) {
        return boardService.insertBoard(dto);
    }

    @GetMapping("/{boardId}")
    public BoardResponseDto.FindBoard getBoard(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

}
