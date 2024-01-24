package com.backend.johnny.dto.Member;

import lombok.Getter;
import lombok.Setter;

public class MemberRequestDto {

    @Getter
    @Setter
    public static class InsertDto {
        private String email;
        private String password;
        private String name;
    }

    @Getter
    @Setter
    public static class LoginDto {
        private String email;
        private String password;
    }

}
