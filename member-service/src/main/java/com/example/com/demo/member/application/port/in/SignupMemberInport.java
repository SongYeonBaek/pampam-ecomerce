package com.example.com.demo.member.application.port.in;

import com.example.com.demo.member.common.BaseResponse;
import com.example.com.demo.member.domain.Member;

public interface SignupMemberInport {
    BaseResponse<Member> signupMember(SignupMemberCommand command);
}
