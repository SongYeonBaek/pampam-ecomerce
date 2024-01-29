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
    private final String consumerAddress;
    private final String consumerName;
    private final String consumerPhoneNum;
    private final String authority;
//    private final Boolean status;
}
