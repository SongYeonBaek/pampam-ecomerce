package com.example.com.demo.member.adapter.out.persistence;

import com.example.com.demo.member.application.port.out.LoginMemberOutport;
import com.example.com.demo.member.application.port.out.SignupMemberOutport;
import com.example.com.demo.member.domain.Member;
import com.example.demo.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements SignupMemberOutport, LoginMemberOutport {
    private final SpringDataMemberRepository memberRepository;

    @Override
    public MemberJpaEntity signupMember(Member member) {
        return memberRepository.save(MemberJpaEntity.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .consumerName(member.getConsumerName())
                .consumerPhoneNum(member.getConsumerPhoneNum())
                .consumerAddress(member.getConsumerAddress())
                .authority("CONSUMER")
                .build());
    }

    @Override
    public MemberJpaEntity loginMember(Member member) {
        Optional<MemberJpaEntity> memberInfo = memberRepository.findByEmail(member.getEmail());

        if (memberInfo.isPresent()) {
            if (memberInfo.get().getPassword().equals(member.getPassword())) {
                return memberInfo.get();
            }
        } else {
            return null;
        }
        return null;
    }
}
