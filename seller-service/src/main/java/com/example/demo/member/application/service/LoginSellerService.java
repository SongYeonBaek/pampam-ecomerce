package com.example.demo.member.application.service;

import com.example.demo.member.adapter.out.persistence.SellerJpaEntity;
import com.example.demo.member.application.port.in.LoginSellerCommand;
import com.example.demo.member.application.port.in.LoginSellerInport;
import com.example.demo.member.application.port.out.LoginSellerJwtOutport;
import com.example.demo.member.application.port.out.LoginSellerOutport;
import com.example.demo.member.common.BaseResponse;
import com.example.demo.member.domain.Seller;
import com.example.demo.member.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSellerService implements LoginSellerInport {

    private final LoginSellerJwtOutport loginSellerJwtOutport;
    private final LoginSellerOutport loginSellerOutport;

    @Override
    public BaseResponse<Object> login(LoginSellerCommand command) {

        SellerJpaEntity seller = loginSellerOutport.loginMember(Seller.builder()
                .email(command.getEmail())
                .sellerPW(command.getPassword())
                .build());

        if (seller != null) {
            String accessToken = loginSellerJwtOutport.generateAccessToken(Seller.builder()
                    .id(seller.getId())
                    .email(seller.getEmail())
                    .sellerName(seller.getSellerName())
                    .sellerPhoneNum(seller.getSellerPhoneNum())
                    .sellerAddr(seller.getSellerAddr())
                    .authority(seller.getAuthority())
                    .build());

            return BaseResponse.successResponse("로그인이 정상적으로 처리되었습니다.", accessToken);
        } else {
            return BaseResponse.failResponse(ErrorCode.USER_NOT_FOUND.getCode(), "존재하지 않는 회원입니다.");
        }
    }
}
