package com.example.social.application.port.in;

import com.example.social.domain.SocialLogin;

public interface SocialSignUpInport {
    SocialLogin createSocialSignUp(SocialSignUpCommand command);
}
