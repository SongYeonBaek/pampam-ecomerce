package com.example.demo.member.application.port.out;


import com.example.demo.member.adapter.out.persistence.SellerJpaEntity;
import com.example.demo.member.application.port.in.SignupSellerCommand;
import com.example.demo.member.domain.Seller;

public interface SignupSellerOutport {
    SellerJpaEntity signupSeller(Seller seller);
}
