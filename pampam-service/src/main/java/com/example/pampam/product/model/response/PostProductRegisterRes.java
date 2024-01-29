package com.example.pampam.product.model.response;

import com.example.pampam.product.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class PostProductRegisterRes {
    private Long idx;

    public static PostProductRegisterRes entityToDto(Product product) {
        return PostProductRegisterRes.builder()
                .idx(product.getIdx())
                .build();
    }
}