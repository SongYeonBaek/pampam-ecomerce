package com.example.demo.member.application.service;


import com.example.demo.common.UseCase;
import com.example.demo.member.application.port.in.SignupSellerCommand;
import com.example.demo.member.application.port.in.SignupSellerInport;
import com.example.demo.member.application.port.out.SignupSellerOutport;
import com.example.demo.member.domain.Seller;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SignupSellerService implements SignupSellerInport {
    private final SignupSellerOutport signupSellerOutport;

    @Override
    public Seller signupSeller(SignupSellerCommand command) {
        Seller seller = Seller.builder()
                .email(command.getEmail())
                .sellerPW(command.getPassword())
                .build();

        Seller signupSeller = signupSellerOutport.signupSeller(seller);

        return Seller.builder()
                .id(signupSeller.getId())
                .email(signupSeller.getEmail())
                .sellerPW(signupSeller.getSellerPW())
                .build();
    }
}

