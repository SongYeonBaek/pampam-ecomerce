package com.example.com.demo.member.adapter.out.persistence;

import com.example.com.demo.member.application.port.out.LoginMemberOutport;
import com.example.com.demo.member.application.port.out.SignupMemberOutport;
import com.example.com.demo.member.common.BaseResponse;
import com.example.com.demo.member.domain.Member;
import com.example.com.demo.member.exception.EcommerceApplicationException;
import com.example.com.demo.member.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@RequiredArgsConstructor
@Component
public class MemberPersistenceAdapter implements SignupMemberOutport, LoginMemberOutport {
    private final SpringDataMemberRepository memberRepository;

    @Override
    public MemberJpaEntity signupMember(Member member) {

        memberRepository.findByEmail(member.getEmail()).ifPresent(it -> {
            throw new EcommerceApplicationException(ErrorCode.DUPLICATE_USER,
                    String.format("%s은 이미 존재하는 회원입니다.", member.getEmail()), ErrorCode.DUPLICATE_USER.getCode());
        });

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
            throw new EcommerceApplicationException(ErrorCode.INVALID_PASSWORD, "비밀번호가 틀립니다.", ErrorCode.INVALID_PASSWORD.getCode());
        }
        throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND.getCode());
    }
}
