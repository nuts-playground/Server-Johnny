package com.backend.johnny.service;

import com.backend.johnny.dto.Member.MemberRequestDto;
import com.backend.johnny.entity.Member;
import com.backend.johnny.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public void insertMember(MemberRequestDto.InsertDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String name = requestDto.getName();

        // password 암호화 하기

        Member member = Member.of(email, password, name);

        Member save = memberRepository.save(member);

        Long memberId = save.getMemberId();

    }

    public void login(MemberRequestDto.LoginDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        String email = requestDto.getEmail();
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is null");
        }
        Member findMember = memberRepository.findByEmail(email);

        if (findMember != null) {
            response.addHeader("Authorization", "jwt");
        }

    }

}
