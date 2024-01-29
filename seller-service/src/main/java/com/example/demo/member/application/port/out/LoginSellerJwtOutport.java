package com.example.demo.member.application.port.out;


import com.example.demo.member.domain.Seller;

public interface LoginSellerJwtOutport {
    String generateAccessToken(Seller seller);

    boolean validateToken(String token);
    String parseMemberIdFromToken(String token);
}
