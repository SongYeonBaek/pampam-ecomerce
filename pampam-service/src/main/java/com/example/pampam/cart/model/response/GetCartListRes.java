package com.example.pampam.cart.model.response;


import com.example.pampam.cart.model.entity.Cart;
import com.example.pampam.product.model.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class GetCartListRes {
    @NotNull
    private Long idx;
    @NotNull
    private Long productIdx;
    @NotNull
    private String productName;
    @NotNull
    private Integer price;
    @NotNull
    private String image;

    public static GetCartListRes entityToDto(Cart cart, Product product) {
        return GetCartListRes.builder()
                .idx(cart.getIdx())
                .productName(product.getProductName())
                .price(product.getPrice())
                .image(product.getImages().get(0).getImagePath())
                .build();
    }
}
