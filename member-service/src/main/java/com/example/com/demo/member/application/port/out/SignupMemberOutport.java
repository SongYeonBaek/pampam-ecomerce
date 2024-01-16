package com.example.com.demo.member.application.port.out;

import com.example.com.demo.member.adapter.out.persistence.MemberJpaEntity;
import com.example.com.demo.member.domain.Member;

public interface SignupMemberOutport {
    MemberJpaEntity signupMember(Member member);
}
