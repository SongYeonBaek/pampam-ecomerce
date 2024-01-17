package com.example.social.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {
    private final String email;
    private final String accessToken;
    private final String refreshToken;

    public static JwtToken generateJetToken(String email,String accessToken, String refreshToken){
        return new JwtToken(email, accessToken, refreshToken);
    }
}
