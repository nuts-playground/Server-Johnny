package com.backend.johnny.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JohnnyException extends RuntimeException {
    private final ErrorCode errorCode;
}
