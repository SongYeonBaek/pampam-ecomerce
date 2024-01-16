package com.example.pampam.category.model.entity;

import com.example.pampam.product.model.entity.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryToProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_idx")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "product_idx")
    private Product product;
}
