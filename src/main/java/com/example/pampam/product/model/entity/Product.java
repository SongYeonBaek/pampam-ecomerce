package com.example.pampam.product.model.entity;

import com.example.pampam.cart.model.entity.Cart;
import com.example.pampam.category.model.entity.CategoryToProduct;
import com.example.pampam.member.model.entity.Seller;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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

    private String productName;
    private Integer price;
    private Integer salePrice;
    private String productInfo;
    private Integer peopleCount;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM.dd HH.mm", timezone="Asia/Seoul")
    private Date startAt;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    private Date closeAt;
    // TODO: 연관관계 설정 후 외래키 지정
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    // 판매자 테이블과 연관 관계 매핑 설정 완료
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Seller_ID")
    private Seller sellerIdx ;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private List<CategoryToProduct> categoryList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private List<Cart> carts;
}

