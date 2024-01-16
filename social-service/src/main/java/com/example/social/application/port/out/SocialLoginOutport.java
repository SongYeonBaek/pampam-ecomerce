package com.example.social.application.port.out;

import com.example.social.domain.SocialLogin;

public interface SocialLoginOutport {
    String generateAccessToken(SocialLogin loginUser);

    String generateRefreshToken(SocialLogin loginUser);
}
