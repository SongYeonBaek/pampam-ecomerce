package com.example.com.demo.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class Member {
    private final Long id;
    private final String email;
    private final String password;
//    private final Boolean status;
}
