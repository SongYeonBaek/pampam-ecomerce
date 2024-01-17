package com.example.pampam.cart.model.response;


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
}
