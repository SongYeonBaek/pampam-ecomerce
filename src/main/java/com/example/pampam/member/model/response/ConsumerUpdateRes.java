package com.example.pampam.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsumerUpdateRes {
    private Long consumerIdx;
    private String consumerPW;
    private String consumerName;
    private String consumerAddr;
    private String consumerPhoneNum;
    private String email;
    private String authority;
    private Boolean status;
    private Boolean socialLogin;
}
