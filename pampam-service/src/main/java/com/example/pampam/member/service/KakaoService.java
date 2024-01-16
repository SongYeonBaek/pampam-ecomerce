package com.example.pampam.member.service;

import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.member.model.request.KakaoEmailReq;
import com.example.pampam.member.repository.ConsumerRepository;
import com.example.pampam.utils.JwtUtils;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@CrossOrigin("*")
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final ConsumerRepository consumerRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    public KakaoEmailReq getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                Object.class
        );

        String str = ("" + response.getBody());
        System.out.println(str);
        Gson gson = new Gson();
        System.out.println(str.split(",")[0]+","+str.split(",")[2]+"}");
        Map<String, Object> result = gson.fromJson(str.split(",")[0]+","+str.split(",")[2]+"}", Map.class);
        Map<String, Object> properties = (Map<String, Object>)result.get("properties");


        System.out.println("username : " + (String)properties.get("nickname"));

        KakaoEmailReq kakaoEmailReq = KakaoEmailReq.builder()
                .consumerName((String)properties.get("nickname"))
                .email((String)properties.get("nickname")+"@kakao.com")
                .build();

        return kakaoEmailReq;
    }

    public String getKakaoToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "e9fc0c68c668c5fe7da688b1ea7a2537");
        params.add("redirect_uri", "http://localhost:8080/member/kakao");
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

    public String kakaoLogin(Consumer consumer) {
        return JwtUtils.generateAccessToken(consumer, secretKey, expiredTimeMs);
    }

    public Consumer kakaoSignup(KakaoEmailReq kakaoEmailReq) {
        Consumer consumer = consumerRepository.save(Consumer.builder()
                .email(kakaoEmailReq.getEmail())
                .consumerPW(passwordEncoder.encode("kakao"))
                        .consumerName(kakaoEmailReq.getConsumerName())
                        .consumerAddr("")
                        .consumerPhoneNum("")
                .authority("CONSUMER")
                        .socialLogin(true)
                        .status(true)
                .build());
        return consumer;
    }
//    public Consumer getKakaoByEmail(GetKakaoByEmailReq getKakaoByEmailReq) {
//        Optional<Consumer> result = consumerRepository.findByEmail(getKakaoByEmailReq.getEmail());
//        if (result.isPresent()) {
//            return result.get();
//        }
//        return null;
//    }
}
