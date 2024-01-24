package com.backend.johnny.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Timestamp {

    private Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;

    private String password;

    private String name;

    public static Member of(String email, String password, String name) {
        return new Member(email, password, name);
    }

}
