package com.backend.johnny.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

public interface QBoardRepository {
}

@Repository
class QBoardRepositoryImpl implements QBoardRepository {

    private EntityManager entityManager;
    private JPAQueryFactory query;

    public QBoardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQueryFactory(entityManager);
    }
}