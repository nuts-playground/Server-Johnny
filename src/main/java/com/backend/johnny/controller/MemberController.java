package com.backend.johnny.controller;

import com.backend.johnny.dto.Member.MemberRequestDto;
import com.backend.johnny.dto.Member.MemberResponseDto;
import com.backend.johnny.dto.ResponseDto;
import com.backend.johnny.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseDto insertMember(@RequestBody MemberRequestDto.InsertDto requestDto) {
        MemberResponseDto.InsertResponseDto responseDto = memberService.insertMember(requestDto);

        return new ResponseDto(responseDto);
    }

    @PostMapping("/login")
    public String loginMember(@RequestBody MemberRequestDto.LoginDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        memberService.login(requestDto, request, response);

        return "ok";
    }

}
