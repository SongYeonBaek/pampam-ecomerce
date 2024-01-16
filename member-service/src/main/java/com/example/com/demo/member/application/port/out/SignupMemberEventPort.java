package com.example.com.demo.member.application.port.out;


import com.example.com.demo.member.domain.Member;

public interface SignupMemberEventPort {
    void signupMemberEvent(Member member);
}
