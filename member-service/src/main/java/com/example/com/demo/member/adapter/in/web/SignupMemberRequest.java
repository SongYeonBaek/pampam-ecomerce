package com.example.com.demo.member.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupMemberRequest {
    private String email;
    private String password;
    private String consumerName;
    private String consumerAddress;
    private String consumerPhoneNum;
//    private Boolean status;
}
