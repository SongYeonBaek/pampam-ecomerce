package com.example.pampam.member.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsumerUpdateReq {
    private String email;
    private String consumerPW;
    private String consumerName;
    private String consumerAddr;
    private String consumerPhoneNum;

}
