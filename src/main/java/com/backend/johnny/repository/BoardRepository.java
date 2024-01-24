package com.backend.johnny.repository;

import com.backend.johnny.entity.Board;
import com.backend.johnny.repository.querydsl.QBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, QBoardRepository {
}
