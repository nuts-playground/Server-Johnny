package com.backend.johnny.repository;

import com.backend.johnny.entity.Member;
import com.backend.johnny.repository.querydsl.QMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, QMemberRepository {
    Member findByEmail(String email);
}
