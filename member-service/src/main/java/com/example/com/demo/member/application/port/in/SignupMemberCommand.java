package com.example.com.demo.member.application.port.in;

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

}
