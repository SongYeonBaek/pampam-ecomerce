package com.example.com.demo.member.application.service;

import com.example.com.demo.member.adapter.out.persistence.MemberJpaEntity;
import com.example.com.demo.member.application.port.in.SignupMemberCommand;
import com.example.com.demo.member.application.port.in.SignupMemberInport;
import com.example.com.demo.member.application.port.out.SignupMemberEventPort;
import com.example.com.demo.member.application.port.out.SignupMemberOutport;
import com.example.com.demo.member.common.BaseResponse;
import com.example.com.demo.member.domain.Member;
import com.example.com.demo.member.exception.ErrorCode;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SignupMemberService implements SignupMemberInport {
    private final SignupMemberOutport signupMemberOutport;
    private final SignupMemberEventPort signupMemberEventPort;

    @Override
    public BaseResponse<Member> signupMember(SignupMemberCommand command) {
        Member member = Member.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .consumerAddress(command.getConsumerAddress())
                .consumerName(command.getConsumerName())
                .consumerPhoneNum(command.getConsumerPhoneNum())
                .build();

        MemberJpaEntity memberJpaEntity = signupMemberOutport.signupMember(member);

        if (memberJpaEntity != null) {
            signupMemberEventPort.signupMemberEvent(member);

            Member result = Member.builder()
                    .id(memberJpaEntity.getId())
                    .email(memberJpaEntity.getEmail())
                    .build();

            return BaseResponse.successResponse("회원가입이 정상적으로 처리되었습니다.", result);

        } else {
            return BaseResponse.failResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), "서버에서 문제가 발생하였습니다.");
        }
    }
}
