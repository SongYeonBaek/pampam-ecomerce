package com.example.social.application.service;

import com.example.demo.common.UseCase;
import com.example.social.adapter.out.persistence.SocialLoginJpaEntity;
import com.example.social.adapter.out.social.LoginUserInfo;
import com.example.social.application.port.in.SocialSignUpCommand;
import com.example.social.application.port.in.SocialSignUpInport;
import com.example.social.application.port.out.SocialApiOutport;
import com.example.social.application.port.out.SocialSignUpOutport;
import com.example.social.domain.SocialLogin;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class SocialSignUpService implements SocialSignUpInport {

    private final SocialSignUpOutport socialLoginOutport;
    private final SocialApiOutport apiOutport;

    @Override
    public SocialLogin createSocialSignUp(SocialSignUpCommand command) {

        String accessToken = getToken(command.getCode());
        LoginUserInfo userInfo = apiOutport.socialLoginApi(accessToken);

        SocialLoginJpaEntity socialLoginUser = socialLoginOutport.socialSignUp(SocialLogin.builder()
                .consumerName(userInfo.getConsumerName())
                .email(userInfo.getEmail())
                .status(true)
                .build());

        return SocialLogin.builder()
                .consumerName(socialLoginUser.getName())
                .email(socialLoginUser.getEmail())
                .build();
    }

    private String getToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "e4ba4375baeaab7e90d2adb66c6bfa80");
        params.add("redirect_uri", "http://localhost:7040/social/signup");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                Object.class
        );

        Gson gson = new Gson();
        Map<String, Object> result = gson.fromJson("" + response.getBody(), Map.class);
        String accessToken = "" + result.get("access_token");

        return accessToken;
    }
}
