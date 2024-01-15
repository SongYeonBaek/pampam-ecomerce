package com.example.pampam.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//포트원 결제 서비스 서버 연결하기
@Configuration
public class PortoneConfig {
    @Value("${imp.apiKey}")
    private String apiKey;
    @Value("${imp.secretKey}")
    private String apiSecret;

    @Bean
    public IamportClient iamportClient(){
        return new IamportClient(apiKey, apiSecret);
    }

}
