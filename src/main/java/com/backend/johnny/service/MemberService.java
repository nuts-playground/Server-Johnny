package com.backend.johnny.service;

import com.backend.johnny.dto.Member.MemberRequestDto;
import com.backend.johnny.dto.Member.MemberResponseDto;
import com.backend.johnny.entity.Member;
import com.backend.johnny.exceptionhandler.ErrorCode;
import com.backend.johnny.exceptionhandler.JohnnyException;
import com.backend.johnny.repository.MemberRepository;
import com.backend.johnny.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtUtil jwtUtil;

    public MemberResponseDto.InsertResponseDto insertMember(MemberRequestDto.InsertDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String name = requestDto.getName();

        //TODO 각각 형식에 맞는 유효성검사 추가해야함
        if (email == null || email.isEmpty()) {
            throw new JohnnyException(ErrorCode.EMAIL_IS_NULL);
        }
        if (password == null || password.isEmpty()) {
            throw new JohnnyException(ErrorCode.PASSWORD_IS_NULL);
        }
        if (name == null || name.isEmpty()) {
            throw new JohnnyException(ErrorCode.NAME_IS_NULL);
        }

        // password 암호화 하기
        String encodePassword = bCryptPasswordEncoder.encode(password);

        Member member = Member.of(email, encodePassword, name);

        Member save = memberRepository.save(member);

        Long memberId = save.getMemberId();

        return new MemberResponseDto.InsertResponseDto(memberId);

    }

    public void login(MemberRequestDto.LoginDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        if (email == null || email.isEmpty()) {
            throw new JohnnyException(ErrorCode.EMAIL_IS_NULL);
        }
        if (password == null || password.isEmpty()) {
            throw new JohnnyException(ErrorCode.PASSWORD_IS_NULL);
        }

        Member findMember = memberRepository.findByEmail(email);
        if (findMember == null) {
            throw new JohnnyException(ErrorCode.MEMBER_IS_NULL);
        }

        String encodePassword = findMember.getPassword();

        if (!bCryptPasswordEncoder.matches(password, encodePassword)) {
            throw new JohnnyException(ErrorCode.PASSWORD_ERROR);
        }

        String jwt = jwtUtil.createJwt(email);
        response.addHeader(JwtUtil.HEADER_STIRNG, JwtUtil.JWT_PREFIX + jwt);

    }

}
