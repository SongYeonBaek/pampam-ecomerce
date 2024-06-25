package com.example.pampam.category.model.response;

import com.example.pampam.product.model.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
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
