package com.example.com.demo.member.adapter.out.jwt;

import com.example.com.demo.member.application.port.out.LoginMemberJwtOutport;
import com.example.com.demo.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider implements LoginMemberJwtOutport {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token-exp-ms}")
    private Integer accessTokenExpirationMs;
    
    public static Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public String generateAccessToken(Member member) {

        Claims claims = Jwts.claims();
        claims.put("idx", member.getId());
        claims.put("email", member.getEmail());
        claims.put("name", member.getConsumerName());
        claims.put("phoneNum", member.getConsumerPhoneNum());
        claims.put("address", member.getConsumerAddress());
        claims.put("authority", member.getAuthority());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public String parseMemberIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
