package com.backend.johnny.dto;

import com.backend.johnny.exceptionhandler.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
public class ErrorResponseDto {

    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private int status;
    private String error;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseDto> responseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponseDto.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

}
