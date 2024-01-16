package com.example.social.application.port.in;

import com.example.social.domain.JwtToken;

public interface SocialLoginInport {
    JwtToken socialLogin(SocialLoginCommand build);
}
