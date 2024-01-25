package com.example.email.adapter.in.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyEmailCertRequest {
    private String email;
    private String uuid;
}
