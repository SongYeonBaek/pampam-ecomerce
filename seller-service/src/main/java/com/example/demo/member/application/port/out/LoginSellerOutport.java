package com.example.demo.member.application.port.out;


import com.example.demo.member.adapter.out.persistence.SellerJpaEntity;
import com.example.demo.member.domain.Seller;

public interface LoginSellerOutport {
    SellerJpaEntity loginMember(Seller seller);
}
