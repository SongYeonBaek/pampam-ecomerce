package com.example.demo.member.application.service;


import com.example.demo.common.UseCase;
import com.example.demo.member.adapter.out.persistence.SellerJpaEntity;
import com.example.demo.member.application.port.in.SignupSellerCommand;
import com.example.demo.member.application.port.in.SignupSellerInport;
import com.example.demo.member.application.port.out.SignupSellerEventPort;
import com.example.demo.member.application.port.out.SignupSellerOutport;
import com.example.demo.member.application.port.out.UploadSellerImagePort;
import com.example.demo.member.domain.Seller;
import com.example.demo.member.domain.SellerImage;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SignupSellerService implements SignupSellerInport {
    private final SignupSellerOutport signupSellerOutport;
    private final SignupSellerEventPort signupSellerEventPort;
    private final UploadSellerImagePort uploadSellerImagePort;
    @Override
    public Seller signupSeller(SignupSellerCommand command) {
        Seller seller = Seller.builder()
                .email(command.getEmail())
                .sellerPW(command.getSellerPW())
                .sellerName(command.getSellerName())
                .sellerAddr(command.getSellerAddr())
                .sellerPhoneNum(command.getSellerPhoneNum())
                .sellerBusinessNumber(command.getSellerBusinessNumber())
                .build();

        SellerJpaEntity sellerJpaEntity = signupSellerOutport.signupSeller(seller);

        signupSellerEventPort.signupSellerEvent(Seller.builder()
                .id(sellerJpaEntity.getId())
                .email(sellerJpaEntity.getEmail())
                .build());

        uploadSellerImagePort.uploadSellerImagePort(SellerImage.builder()
                .email(sellerJpaEntity.getEmail())
                .file(command.getFile())
                .build());


        return Seller.builder()
                .id(sellerJpaEntity.getId())
                .email(sellerJpaEntity.getEmail())
                .sellerPW(sellerJpaEntity.getSellerPW())
                .sellerName(sellerJpaEntity.getSellerName())
                .sellerAddr(sellerJpaEntity.getSellerAddr())
                .sellerPhoneNum(sellerJpaEntity.getSellerPhoneNum())
                .sellerBusinessNumber(sellerJpaEntity.getSellerBusinessNumber())
                .build();
    }
}

