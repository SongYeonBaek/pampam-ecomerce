package com.example.social.adapter.out.social;

import com.example.demo.common.ExternalSystemAdapter;
import com.example.social.application.port.out.SocialApiOutport;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@ExternalSystemAdapter
public class SocialLoginAdapter implements SocialApiOutport {

    @Override
    public LoginUserInfo socialLoginApi(String accessToken) {
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

        String responseBody = ("" + response.getBody());
        System.out.println(responseBody);
        Gson gson = new Gson();
        System.out.println(responseBody.split(",")[0]+","+responseBody.split(",")[2]+"}");

        Map<String, Object> result = gson.fromJson(responseBody.split(",")[0]+","+responseBody.split(",")[2]+"}", Map.class);
        Map<String, Object> properties = (Map<String, Object>)result.get("properties");


        System.out.println("username : " + (String)properties.get("nickname"));
        String userInfo = (String) properties.get("nickname");

        return LoginUserInfo.builder()
                .consumerName(userInfo)
                .email(userInfo + "@kakao.com")
                .build();

    }
}
