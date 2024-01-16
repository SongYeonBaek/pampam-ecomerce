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
    private Seller sellerIdx ;
    private Integer peopleCount;
    private Date startAt;
    private Date closeAt;
}