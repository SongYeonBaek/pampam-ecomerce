package com.example.pampam.category.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetSearchRes {
    private Long productIdx;
    private Long sellerIdx;
    private String productName;
    private Integer price;
    private String image;
}
