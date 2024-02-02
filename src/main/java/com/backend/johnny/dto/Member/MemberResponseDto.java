package com.backend.johnny.dto.Member;

import lombok.Getter;
import lombok.Setter;

public class MemberResponseDto {

    @Getter
    @Setter
    public static class InsertResponseDto {
        private Long memberId;

        public InsertResponseDto(Long memberId) {
            this.memberId = memberId;
        }
    }

}
