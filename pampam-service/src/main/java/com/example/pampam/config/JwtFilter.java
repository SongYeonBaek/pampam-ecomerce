package com.example.pampam.config;

import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JwtFilter extends OncePerRequestFilter {

    private final String secretKey;

    public JwtFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, MalformedJwtException, ExpiredJwtException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        String token;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.split(" ")[1];
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);

            filterChain.doFilter(request, response);
            return;
        }

        Long idx = JwtUtils.getUserIdx(token, secretKey);
        String username = JwtUtils.getUsername(token, secretKey);
        String role = JwtUtils.getAuthority(token, secretKey);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        if (!JwtUtils.validate(token, username, secretKey)) {
            filterChain.doFilter(request, response);
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                Consumer.builder().consumerIdx(idx).consumerName(username).build(), null,
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인가하는 코드
        filterChain.doFilter(request, response);

    }
}