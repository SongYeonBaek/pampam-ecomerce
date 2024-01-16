package com.example.com.demo.member.application.service;

import com.example.com.demo.member.adapter.out.persistence.MemberJpaEntity;
import com.example.com.demo.member.application.port.in.SignupMemberCommand;
import com.example.com.demo.member.application.port.in.SignupMemberInport;
import com.example.com.demo.member.application.port.out.SignupMemberOutport;
import com.example.com.demo.member.domain.Member;
import com.example.demo.common.UseCase;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SignupMemberService implements SignupMemberInport {
    private final SignupMemberOutport signupMemberOutport;

    @Override
    public Member signupMember(SignupMemberCommand command) {
        Member member = Member.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .build();

        MemberJpaEntity memberJpaEntity = signupMemberOutport.signupMember(member);

        return Member.builder()
                .id(memberJpaEntity.getId())
                .email(memberJpaEntity.getEmail())
                .password(memberJpaEntity.getPassword())
                .build();
    }
}
