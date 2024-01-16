package com.example.social.adapter.out.persistence;

import com.example.demo.common.PersistenceAdapter;
import com.example.social.application.port.out.SocialLoginCheckOutport;
import com.example.social.application.port.out.SocialSignUpOutport;
import com.example.social.domain.SocialLogin;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class SocialPersistenceAdapter implements SocialSignUpOutport, SocialLoginCheckOutport {

    private final SocialRepository socialLoginRepository;

    @Override
    public SocialLoginJpaEntity socialSignUp(SocialLogin socialUser) {
        SocialLoginJpaEntity loginUser = socialLoginRepository.save(SocialLoginJpaEntity.builder()
                .name(socialUser.getConsumerName())
                .email(socialUser.getEmail())
                .status(socialUser.getStatus())
                .build());

        return loginUser;
    }

    @Override
    public SocialLoginJpaEntity socialLogin(SocialLogin socialUser) {
        Optional<SocialLoginJpaEntity> user = socialLoginRepository.findByEmail(socialUser.getEmail());

        if (user.isPresent()) {
            return SocialLoginJpaEntity.builder()
                    .socialIdx(user.get().getSocialIdx())
                    .email(user.get().getEmail())
                    .name(user.get().getName())
                    .build();
        } else {
            return null;
        }
    }
}
