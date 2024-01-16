package com.example.email.adapter.in.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmailCertRequest {
    // 카프카가 메세지를 보내는 형식
    Map<String, String> record;
}
