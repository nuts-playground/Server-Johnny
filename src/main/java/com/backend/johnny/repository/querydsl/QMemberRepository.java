package com.backend.johnny.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

public interface QMemberRepository {
}

@Repository
class QMemberRepositoryImpl implements QMemberRepository {

    private EntityManager entityManager;
    private JPAQueryFactory query;

    public QMemberRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQueryFactory(entityManager);
    }
}
