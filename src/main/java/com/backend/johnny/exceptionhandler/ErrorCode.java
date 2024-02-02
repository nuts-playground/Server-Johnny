package com.backend.johnny.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 400
    EMAIL_IS_NULL(HttpStatus.BAD_REQUEST, "email is null"),
    PASSWORD_IS_NULL(HttpStatus.BAD_REQUEST, "password is null"),
    NAME_IS_NULL(HttpStatus.BAD_REQUEST, "name is null"),
    MEMBER_IS_NULL(HttpStatus.BAD_REQUEST, "that email member is null"),

    // 401
    PASSWORD_ERROR(HttpStatus.UNAUTHORIZED, "password is invalid"),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "jwt is expired"),
    JWT_IS_NULL(HttpStatus.UNAUTHORIZED, "jwt is null"),
    JWT_IS_NOT_BEARER(HttpStatus.UNAUTHORIZED, "jwt is not bearer");


    // 403

    // 404

    // 500

    private final HttpStatus httpStatus;
    private final String message;

}
