package com.example.com.demo.member.application.port.in;

import com.example.com.demo.member.domain.Member;

public interface SignupMemberInport {
    Member signupMember(SignupMemberCommand command);
}
