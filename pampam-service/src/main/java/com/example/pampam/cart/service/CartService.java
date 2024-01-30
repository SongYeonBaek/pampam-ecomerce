package com.example.pampam.cart.service;

import com.example.pampam.cart.model.entity.Cart;
import com.example.pampam.cart.model.response.GetCartListRes;
import com.example.pampam.cart.model.response.PostCartInRes;
import com.example.pampam.cart.repository.CartRepository;
import com.example.pampam.common.BaseResponse;
import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.member.repository.ConsumerRepository;
import com.example.pampam.product.model.entity.Product;
import com.example.pampam.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ConsumerRepository consumerRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public BaseResponse<PostCartInRes> cartIn(Long productIdx, String token) {
        token = JwtUtils.replaceToken(token);
        Claims consumerInfo = JwtUtils.getConsumerInfo(token, secretKey);
        Long consumerIdx = consumerInfo.get("idx", Long.class);
        String authority = consumerInfo.get("authority", String.class);

        if (authority.equals("CONSUMER")) {
            if (consumerIdx != null) {
                Cart cart = cartRepository.save(Cart.cartBuilder(productIdx, consumerIdx));
                PostCartInRes product = PostCartInRes.entityToDto(cart);

                return BaseResponse.successResponse("요청 성공", product);
            } else {
                throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND);
            }
        } else {
            return BaseResponse.failResponse(7000, "판매자는 접근이 불가합니다.");
        }
    }

    public BaseResponse<List<GetCartListRes>> cartList(String token) {
        token = JwtUtils.replaceToken(token);
        Long consumerIdx = JwtUtils.getUserIdx(token, secretKey);

        if (consumerIdx != null) {
            List<Cart> carts = cartRepository.findAllByConsumerIdx(consumerIdx);
            List<GetCartListRes> cartList = new ArrayList<>();

            for (Cart cart : carts) {
                Product product = cart.getProduct();
                cartList.add(GetCartListRes.entityToDto(cart, product));
            }
            return BaseResponse.successResponse("요청 성공", cartList);
        } else {
            throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public BaseResponse<String> updateCart(String token, Long cartIdx) {
        token = JwtUtils.replaceToken(token);
        Long consumerIdx = JwtUtils.getUserIdx(token, secretKey);

        Claims consumerInfo = JwtUtils.getConsumerInfo(token, secretKey);

        if (consumerIdx != null) {
            cartRepository.deleteById(cartIdx);
            return BaseResponse.successResponse("요청 성공", consumerInfo.get("email", String.class));
        } else {
            throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND);
        }
    }
}