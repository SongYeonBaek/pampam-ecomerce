package com.example.pampam.product.model.response;

import com.example.pampam.member.model.entity.Seller;
import com.example.pampam.product.model.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class GetProductReadRes {
    private Long idx;
    private String productName;
    private Integer price;
    private Integer salePrice;
    private String productInfo;
    private String filename;
    private Long sellerIdx ;
    private Integer peopleCount;
    private Date startAt;
    private Date closeAt;

    public static GetProductReadRes entityToDto(Product product, String filename) {
        return GetProductReadRes.builder()
                .idx(product.getIdx())
                .productName(product.getProductName())
                .price(product.getPrice())
                .salePrice(product.getSalePrice())
                .productInfo(product.getProductInfo())
                .filename(filename)
                .sellerIdx(product.getSellerIdx())
                .peopleCount(product.getPeopleCount())
                .startAt(product.getStartAt())
                .closeAt(product.getCloseAt())
                .build();
    }
}