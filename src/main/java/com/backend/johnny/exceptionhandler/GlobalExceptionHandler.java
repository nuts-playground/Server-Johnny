package com.backend.johnny.exceptionhandler;

import com.backend.johnny.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {JohnnyException.class})
    protected ResponseEntity<ErrorResponseDto> handleJohnnyException(JohnnyException e) {
        log.error("Error response : " + e.getErrorCode().name());
        return ErrorResponseDto.responseEntity(e.getErrorCode());
    }

}
