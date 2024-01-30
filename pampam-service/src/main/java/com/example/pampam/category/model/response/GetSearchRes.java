package com.example.pampam.category.model.response;

import com.example.pampam.category.model.entity.CategoryToProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class GetSearchRes {
    @NotNull
    private Long productIdx;
    @NotNull
    private Long sellerIdx;
    @NotNull
    private String productName;
    @NotNull
    private Integer price;
    private String image;

    public static GetSearchRes entityToDto(CategoryToProduct categoryToProduct) {
        return GetSearchRes.builder()
                .productIdx(categoryToProduct.getProduct().getIdx())
                .productName(categoryToProduct.getProduct().getProductName())
                .price(categoryToProduct.getProduct().getPrice())
                .image(categoryToProduct.getProduct().getImages().get(0).getImagePath())
                .sellerIdx(categoryToProduct.getProduct().getSellerIdx())
                .build();
    }
}
