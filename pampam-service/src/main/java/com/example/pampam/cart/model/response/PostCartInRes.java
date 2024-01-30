package com.example.pampam.cart.model.response;

import com.example.pampam.cart.model.entity.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostCartInRes {
    private Long productId;
    private String productName;

    public static PostCartInRes entityToDto(Cart cart) {
        return PostCartInRes.builder()
                .productId(cart.getProduct().getIdx())
                .productName(cart.getProduct().getProductName())
                .build();
    }
}
