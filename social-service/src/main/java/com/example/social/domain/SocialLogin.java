package com.example.social.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialLogin {
    private final String email;
    private final String consumerName;
    private final String accessToken;
    private final String refreshToken;
    private final Boolean status;
}
