package com.example.pampam.config.filter;



import com.example.pampam.exception.CustomJwtSignatureException;
import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.member.model.entity.Seller;
import com.example.pampam.member.service.MemberService;
import com.example.pampam.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token;
            if (header != null && header.startsWith("Bearer ")) {
                token = header.split(" ")[1];
            } else {
                filterChain.doFilter(request, response);
                return;
            }

            String username = JwtUtils.getUsername(token, secretKey);


            // db에서 엔티티 조회
            // member.getUsername();

            Consumer member = memberService.getMemberByConsumerID(username);


            // TODO: 수정이 필요한 코드
            if (member != null) {
                String memberUsername = member.getUsername();


                if (!JwtUtils.validate(token, memberUsername, secretKey)) {
                    filterChain.doFilter(request, response);
                    return;
                }


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        member.getEmail(), null,
                        member.getAuthorities()
                );
                // 인가하는 코드

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                Seller seller = memberService.getMemberBySellerID(username);

                String memberUsername = seller.getUsername();


                if (!JwtUtils.validate(token, memberUsername, secretKey)) {
                    filterChain.doFilter(request, response);
                    return;
                }


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        seller.getEmail(), null,
                        seller.getAuthorities()
                );
                // 인가하는 코드

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        }
        catch (ServletException e) {
            throw new EcommerceApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
//        catch (SignatureException e) {
//
//            throw new CustomJwtSignatureException(ErrorCode.INVALID_TOKEN, e.getMessage());
//        }

        }
    }
