package com.example.pampam.category.model.response;

import com.example.pampam.category.model.entity.Category;
import com.example.pampam.product.model.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class PostInsertRes {
    @NotNull
    private String productName;
    @NotNull
    private String region;

    public static PostInsertRes entityToDto(Product product, Category category) {
        return PostInsertRes.builder()
                .productName(product.getProductName())
                .region(category.getRegion())
                .build();
    }
}
