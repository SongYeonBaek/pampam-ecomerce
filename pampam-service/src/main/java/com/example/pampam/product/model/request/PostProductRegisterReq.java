package com.example.pampam.product.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PostProductRegisterReq {
    private String productName;
    private Integer price;
    private Integer salePrice;
    private String productInfo;

    // TODO: 인원수, 마감일
}
