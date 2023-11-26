package com.backend.johnny.service;

import com.backend.johnny.dto.board.BoardRequestDto;
import com.backend.johnny.dto.board.BoardResponseDto;
import com.backend.johnny.entity.Board;
import com.backend.johnny.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Map<String, Long> insertBoard(BoardRequestDto.InsertDto dto) {
        Board board = Board.of(dto.getTitle(), dto.getContent());

        Board saveBoard = boardRepository.save(board);

        Map<String, Long> result = new HashMap<>();
        result.put("boardId", saveBoard.getBoardId());

        return result;
    }

    public BoardResponseDto.FindBoard getBoard(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isPresent()) {
            Board board = findBoard.get();

            return new BoardResponseDto.FindBoard(board);
        }

        return null;
    }

}
