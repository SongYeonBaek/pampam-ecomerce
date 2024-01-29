package com.example.com.demo.member.application.port.out;

import com.example.com.demo.member.domain.Member;

public interface LoginMemberJwtOutport {
    String generateAccessToken(Member member);

    boolean validateToken(String token);
    String parseMemberIdFromToken(String token);
}
