package com.example.pampam.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsumerSignupRes {
    private Long consumerIdx;
    private String email;
    private String consumerPW;
    private String consumerName;
    private String consumerAddr;
    private String consumerPhoneNum;
    private String authority;
    private Boolean socialLogin;
    private Boolean status;
}
