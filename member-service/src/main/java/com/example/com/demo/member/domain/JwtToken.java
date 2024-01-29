package com.example.com.demo.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class JwtToken {
    private final Long id;
    private final String accessToken;

    public static JwtToken generateJwtToken(Long id, String accessToken) {
        return new JwtToken(id, accessToken);
    }
}
