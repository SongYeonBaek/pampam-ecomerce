package com.example.com.demo.member.application.port.in;

import com.example.com.demo.member.adapter.in.web.SignupMemberRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class SignupMemberCommand {

    private String email;
    private String password;
    private String consumerName;
    private String consumerAddress;
    private String consumerPhoneNum;

    public static SignupMemberCommand buildCommand(SignupMemberRequest request) {
        return SignupMemberCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .consumerName(request.getConsumerName())
                .consumerAddress(request.getConsumerAddress())
                .consumerPhoneNum(request.getConsumerPhoneNum())
                .build();
    }

}
