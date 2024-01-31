package com.example.pampam.product.repository.querydsl;

import com.example.pampam.product.model.entity.Product;

import com.example.pampam.product.model.entity.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {
    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }

    @Override
    public Page<Product> findList(Pageable pageable) {
        QProduct product = new QProduct("product");


        List<Product> result = from(product)
                .leftJoin(product.images).fetchJoin()
//                .leftJoin(product.sellerIdx)
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());

        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }
}
