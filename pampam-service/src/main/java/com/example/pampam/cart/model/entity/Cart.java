package com.example.pampam.cart.model.entity;

import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.product.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long consumerIdx;

    // 상품이랑 연관관계 맺기
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")
    private Product product;

    public static Cart cartBuilder(Long productIdx, Long consumerIdx) {
        return Cart.builder()
                .product(Product.builder().idx(productIdx).build())
                .consumerIdx(consumerIdx)
                .build();
    }
}