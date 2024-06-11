package com.example.pampam.category.model.response;

import com.example.pampam.product.model.entity.Product;
import lombok.Builder;

@Builder
public class GetSearchProductToCategory {
    private Long productIdx;
    private String productName;

    public static GetSearchProductToCategory buildProductToCategory(Product product) {
        return GetSearchProductToCategory.builder()
                .productIdx(product.getIdx())
                .productName(product.getProductName())
                .build();
    }
}
