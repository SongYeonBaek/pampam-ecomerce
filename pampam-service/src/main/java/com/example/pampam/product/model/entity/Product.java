package com.example.pampam.product.model.entity;

import com.example.pampam.cart.model.entity.Cart;
import com.example.pampam.member.model.entity.Seller;
import com.example.pampam.orders.model.entity.OrderedProduct;
import com.example.pampam.product.model.request.PostProductRegisterReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.jsonwebtoken.Claims;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private ProductType productType;

    @Column(nullable = false)
    @Size(max = 100)
    private String productName;

    @Column(nullable = false)
    @Min(0)
    private Integer price;

    @Column(nullable = false)
    @Min(0)
    private Integer salePrice;

    @Column(nullable = false)
    @Size(max = 2000)
    private String productInfo;

    @Column(nullable = false)
    private Integer people; // 모집 인원 수

    @Column(nullable = false)
    private Integer peopleCount; // 모인 인원 수

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM.dd HH.mm", timezone="Asia/Seoul")
    private Date startAt;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    private Date closeAt;

    // 판매자 정보
    private Long sellerIdx;
    private String sellerEmail;
    private String sellerName;
    private String sellerAddr;
    private String sellerPhoneNum;

    // TODO: 연관관계 설정 후 외래키 지정
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private List<Cart> carts;

    @OneToMany(mappedBy = "product")
    private List<OrderedProduct> orderedProducts = new ArrayList<>();

    public static Product dtoToEntity(PostProductRegisterReq productRegisterReq, Claims sellerInfo) {
        return Product.builder()
                .productType(productRegisterReq.getProductType())
                .productName(productRegisterReq.getProductName())
                .productInfo(productRegisterReq.getProductInfo())
                .price(productRegisterReq.getPrice())
                .salePrice(productRegisterReq.getSalePrice())
                .startAt(productRegisterReq.getStartAt())
                .closeAt(productRegisterReq.getCloseAt())
                .people(productRegisterReq.getPeople())
                .peopleCount(productRegisterReq.getPeopleCount())
                .sellerIdx(sellerInfo.get("idx", Long.class))
                .sellerEmail(sellerInfo.get("email", String.class))
                .sellerName(sellerInfo.get("name", String.class))
                .sellerPhoneNum(sellerInfo.get("phoneNum", String.class))
                .sellerAddr(sellerInfo.get("address", String.class))
                .build();
    }
}