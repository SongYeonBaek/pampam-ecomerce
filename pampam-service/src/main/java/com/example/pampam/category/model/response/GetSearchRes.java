package com.example.pampam.category.model.response;

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
}
