package com.backend.johnny.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("")
    public String hello(@RequestParam(required = false) String value) {
        String result = "Hello!";
        if (value != null) {
            result = result + " " + value;
        }
        return result;
    }

    @GetMapping("/2")
    public String hello2(@RequestParam(required = false) String value) {
        if (true) {
            throw new IllegalArgumentException("a");
        }
        String result = "Hello!";
        if (value != null) {
            result = result + " " + value;
        }
        return result;
    }

}
