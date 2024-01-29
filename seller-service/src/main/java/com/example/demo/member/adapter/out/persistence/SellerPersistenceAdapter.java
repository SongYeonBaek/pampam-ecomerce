package com.example.demo.member.adapter.out.persistence;


import com.example.demo.common.PersistenceAdapter;
import com.example.demo.member.application.port.out.LoginSellerOutport;
import com.example.demo.member.application.port.out.SignupSellerOutport;
import com.example.demo.member.domain.Seller;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class SellerPersistenceAdapter implements SignupSellerOutport, LoginSellerOutport {
    private final SpringDataSellerRepository sellerRepository;

    @Override
    public SellerJpaEntity signupSeller(Seller seller) {
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
            }
        }
        return null;
    }
}
