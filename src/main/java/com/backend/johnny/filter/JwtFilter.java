package com.backend.johnny.filter;

import com.backend.johnny.exceptionhandler.ErrorCode;
import com.backend.johnny.exceptionhandler.JohnnyException;
import com.backend.johnny.repository.MemberRepository;
import com.backend.johnny.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private MemberRepository memberRepository;
    private JwtUtil jwtUtil;

    public JwtFilter(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(JwtUtil.HEADER_STIRNG);

        if (authorization == null || authorization.isEmpty()) {
            throw new JohnnyException(ErrorCode.JWT_IS_NULL);
        }

        String[] split = authorization.split(" ");
        if (!"Bearer".equals(split[0])) {
            throw new JohnnyException(ErrorCode.JWT_IS_NOT_BEARER);
        }



    }
}
