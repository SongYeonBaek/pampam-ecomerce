package com.example.pampam.member.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailReq {     //정규식 필요 x)
    private String email;
    private String authority;
    private String accessToken;
}
