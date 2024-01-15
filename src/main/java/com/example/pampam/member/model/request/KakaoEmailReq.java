package com.example.pampam.member.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoEmailReq {
    private String consumerName;
    private String email;
}
