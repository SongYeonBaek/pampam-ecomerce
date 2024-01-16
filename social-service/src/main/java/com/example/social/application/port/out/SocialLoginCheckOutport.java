package com.example.social.application.port.out;

import com.example.social.adapter.out.persistence.SocialLoginJpaEntity;
import com.example.social.domain.SocialLogin;

public interface SocialLoginCheckOutport {

    SocialLoginJpaEntity socialLogin(SocialLogin build);
}
