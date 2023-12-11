package com.backend.johnny.controller;

import com.backend.johnny.service.CfaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cfa")
public class CfaController {

    private final CfaService cfaService;

    @GetMapping("/cookie")
    public String getCookie(@RequestParam(name = "userId") String userId, @RequestParam(name = "userPw") String userPw) {
        return cfaService.getCookie(userId, userPw);
    }

}
