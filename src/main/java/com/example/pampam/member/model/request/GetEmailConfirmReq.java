package com.example.pampam.member.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetEmailConfirmReq {
    private String email;
    private String token;
    private String jwt;
    private String authority;
}
