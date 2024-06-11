package com.example.pampam.category.model.entity;

import com.example.pampam.product.model.entity.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String categoryType;

    @OneToOne(mappedBy = "category")
    private Product product;

    public static Category buildCategory(String categoryType) {
        return Category.builder().categoryType(categoryType).build();
    }
}
