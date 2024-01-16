package com.example.demo.member.adapter.out.persistence;


import com.example.demo.common.PersistenceAdapter;
import com.example.demo.member.application.port.out.SignupSellerOutport;
import com.example.demo.member.domain.Seller;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class SellerPersistenceAdapter implements SignupSellerOutport {
    private final SpringDataSellerRepository memberRepository;

    @Override
    public Seller signupSeller(Seller seller) {
        SellerJpaEntity sellerJpaEntity = memberRepository.save(SellerJpaEntity.builder()
                .email(seller.getEmail())
               .sellerPW(seller.getSellerPW())
                       .sellerName(seller.getSellerName())
                       .sellerPhoneNum(seller.getSellerPhoneNum())
                       .sellerBusinessNumber(seller.getSellerBusinessNumber())
                       .sellerAddr(seller.getSellerAddr())
                .build());

        return Seller.builder()
                .id(sellerJpaEntity.getId())
                .email(sellerJpaEntity.getEmail())
                .sellerPW(sellerJpaEntity.getSellerPW())
                .sellerName(sellerJpaEntity.getSellerName())
                .sellerAddr(sellerJpaEntity.getSellerAddr())
                .sellerBusinessNumber(sellerJpaEntity.getSellerBusinessNumber())
                .sellerPhoneNum(sellerJpaEntity.getSellerPhoneNum())
                .build();
    }
}
