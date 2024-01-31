package com.example.pampam.orders.model.entity;

import com.example.pampam.orders.model.response.GetPortOneRes;
import com.example.pampam.product.model.entity.Product;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Product_id")
    private Product product;

    public static OrderedProduct dtoToEntity(Orders orders, GetPortOneRes getPortOneRes) {
        return OrderedProduct.builder()
                .orders(orders)
                .product(Product.builder().idx(getPortOneRes.getId()).build())
                .build();
    }
}
