package com.example.demo.member.adapter.out.persistence;


import com.example.demo.member.application.port.out.LoginSellerOutport;
import com.example.demo.member.application.port.out.SignupSellerOutport;
import com.example.demo.member.domain.Seller;
import com.example.demo.member.exception.EcommerceApplicationException;
import com.example.demo.member.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SellerPersistenceAdapter implements SignupSellerOutport, LoginSellerOutport {
    private final SpringDataSellerRepository sellerRepository;

    @Override
    public SellerJpaEntity signupSeller(Seller seller) {

        sellerRepository.findByEmail(seller.getEmail()).ifPresent(it -> {
            throw new EcommerceApplicationException(ErrorCode.DUPLICATE_USER,
                    String.format("%s은 이미 존재하는 회원입니다.", seller.getEmail()), ErrorCode.DUPLICATE_USER.getCode());
        });

        return sellerRepository.save(SellerJpaEntity.builder()
                .email(seller.getEmail())
                .sellerPW(seller.getSellerPW())
                .sellerName(seller.getSellerName())
                .sellerPhoneNum(seller.getSellerPhoneNum())
                .sellerBusinessNumber(seller.getSellerBusinessNumber())
                .sellerAddr(seller.getSellerAddr())
                .authority("SELLER")
                .build());
    }

    @Override
    public SellerJpaEntity loginMember(Seller seller) {
        Optional<SellerJpaEntity> sellerInfo = sellerRepository.findByEmail(seller.getEmail());

        if (sellerInfo.isPresent()) {
            if (sellerInfo.get().getSellerPW().equals(seller.getSellerPW())) {
                return sellerInfo.get();
            } else {
                throw new EcommerceApplicationException(ErrorCode.INVALID_PASSWORD, "비밀번호가 틀립니다.", ErrorCode.INVALID_PASSWORD.getCode());
            }
        } else {
            throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND.getCode());
        }
    }
}
