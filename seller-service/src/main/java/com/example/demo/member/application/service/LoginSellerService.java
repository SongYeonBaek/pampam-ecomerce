package com.example.demo.member.application.service;

import com.example.demo.common.UseCase;
import com.example.demo.member.adapter.out.persistence.SellerJpaEntity;
import com.example.demo.member.application.port.in.LoginSellerCommand;
import com.example.demo.member.application.port.in.LoginSellerInport;
import com.example.demo.member.application.port.out.LoginSellerJwtOutport;
import com.example.demo.member.application.port.out.LoginSellerOutport;
import com.example.demo.member.domain.JwtToken;
import com.example.demo.member.domain.Seller;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class LoginSellerService implements LoginSellerInport {

    private final LoginSellerJwtOutport loginSellerJwtOutport;
    private final LoginSellerOutport loginSellerOutport;

    @Override
    public JwtToken login(LoginSellerCommand command) {

        SellerJpaEntity seller = loginSellerOutport.loginMember(Seller.builder()
                .email(command.getEmail())
                .sellerPW(command.getPassword())
                .build());

        String accessToken = loginSellerJwtOutport.generateAccessToken(Seller.builder()
                .id(seller.getId())
                .email(seller.getEmail())
                .sellerName(seller.getSellerName())
                .sellerPhoneNum(seller.getSellerPhoneNum())
                .sellerAddr(seller.getSellerAddr())
                .authority(seller.getAuthority())
                .build());

        return JwtToken.generateJwtToken(accessToken);
    }
}
