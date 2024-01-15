package com.example.pampam.product.model.response;

import com.example.pampam.member.model.entity.Seller;
import com.example.pampam.product.model.entity.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetProductReadRes {
    private Long idx;
    private String productName;
    private Integer price;
    private Integer salePrice;
    private String productInfo;
    private String filename;
    private Seller sellerIdx ;

    public static GetProductReadRes DtoToRes(Product product, String fileNames)
    {
        return GetProductReadRes.builder()
                .idx(product.getIdx())
                .productName(product.getProductName())
                .price(product.getPrice())
                .salePrice(product.getSalePrice())
                .productInfo(product.getProductInfo())
                .filename(fileNames)
                .sellerIdx(product.getSellerIdx())
                .build();
    }

}
