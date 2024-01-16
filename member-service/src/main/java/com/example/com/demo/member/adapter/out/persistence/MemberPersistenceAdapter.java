package com.example.com.demo.member.adapter.out.persistence;

import com.example.com.demo.member.application.port.out.SignupMemberOutport;
import com.example.com.demo.member.domain.Member;
import com.example.demo.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements SignupMemberOutport {
    private final SpringDataMemberRepository memberRepository;

    @Override
    public MemberJpaEntity signupMember(Member member) {
       return memberRepository.save(MemberJpaEntity.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .build());
    }
}
