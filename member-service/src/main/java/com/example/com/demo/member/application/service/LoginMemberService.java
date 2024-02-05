package com.example.com.demo.member.application.service;

import com.example.com.demo.member.adapter.out.persistence.MemberJpaEntity;
import com.example.com.demo.member.application.port.in.LoginMemberCommand;
import com.example.com.demo.member.application.port.in.LoginMemberInport;
import com.example.com.demo.member.application.port.out.LoginMemberJwtOutport;
import com.example.com.demo.member.application.port.out.LoginMemberOutport;
import com.example.com.demo.member.common.BaseResponse;
import com.example.com.demo.member.domain.JwtToken;
import com.example.com.demo.member.domain.Member;
import com.example.com.demo.member.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class LoginMemberService implements LoginMemberInport {

    private final LoginMemberOutport loginMemberOutport;
    private final LoginMemberJwtOutport loginMemberJwtOutport;


    @Override
    public BaseResponse<String> login(LoginMemberCommand command) {
        Member member = Member.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .build();

        MemberJpaEntity result = loginMemberOutport.loginMember(member);

        if (result != null) {
            Member memberInfo = Member.builder()
                    .id(result.getId())
                    .email(result.getEmail())
                    .consumerName(result.getConsumerName())
                    .consumerPhoneNum(result.getConsumerPhoneNum())
                    .consumerAddress(result.getConsumerAddress())
                    .authority(result.getAuthority())
                    .build();

            String accessToken = loginMemberJwtOutport.generateAccessToken(memberInfo);
            return BaseResponse.successResponse("로그인이 정상적으로 처리되었습니다.", accessToken);
        } else {
            return BaseResponse.failResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), "서버에서 문제가 발생하였습니다.");
        }
    }


}
