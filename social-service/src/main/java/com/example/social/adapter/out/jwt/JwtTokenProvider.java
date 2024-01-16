package com.example.social.adapter.out.jwt;

import com.example.demo.common.ExternalSystemAdapter;
import com.example.social.application.port.out.SocialLoginOutport;
import com.example.social.domain.SocialLogin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

@ExternalSystemAdapter
public class JwtTokenProvider implements SocialLoginOutport {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.accessToken-ExpirationMs}")
    private Integer accessTokenMs;
    @Value("${jwt.refreshToken-ExpirationMs}")
    private Integer refreshTokenMs;

    @Override
    public String generateAccessToken(SocialLogin loginUser) {
        Claims claims = Jwts.claims();
        claims.put("email",loginUser.getEmail());
        claims.put("consumerName", loginUser.getConsumerName());


        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenMs * 30))
                .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    @Override
    public String generateRefreshToken(SocialLogin loginUser) {
        Claims claims = Jwts.claims();
        claims.put("email",loginUser.getEmail());
        claims.put("consumerName", loginUser.getConsumerName());
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenMs * 60 * 5))
                .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
