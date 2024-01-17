package com.example.social.application.service;

import com.example.demo.common.UseCase;
import com.example.social.adapter.out.persistence.SocialLoginJpaEntity;
import com.example.social.application.port.in.SocialLoginCommand;
import com.example.social.application.port.in.SocialLoginInport;
import com.example.social.application.port.out.SocialLoginCheckOutport;
import com.example.social.application.port.out.SocialLoginOutport;
import com.example.social.domain.JwtToken;
import com.example.social.domain.SocialLogin;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SocialLoginService implements SocialLoginInport {

    private final SocialLoginCheckOutport socialLoginCheckOutport;
    private final SocialLoginOutport socialLoginOutport;


    @Override
    public JwtToken socialLogin(SocialLoginCommand command) {
        SocialLoginJpaEntity validUser = socialLoginCheckOutport.socialLogin(SocialLogin.builder()
                .email(command.getEmail())
                .build());

        SocialLogin loginUser = SocialLogin.builder()
                .email(validUser.getEmail())
                .consumerName(validUser.getName())
                .build();

        return JwtToken.generateJetToken(
                loginUser.getEmail(),
                socialLoginOutport.generateAccessToken(loginUser),
                socialLoginOutport.generateRefreshToken(loginUser));
    }
}
