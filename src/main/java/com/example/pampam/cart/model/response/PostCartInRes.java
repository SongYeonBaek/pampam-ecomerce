package com.example.pampam.cart.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostCartInRes {
    private Long productId;
    private String productName;
}
