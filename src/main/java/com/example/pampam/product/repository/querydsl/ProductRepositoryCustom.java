package com.example.pampam.product.repository.querydsl;
import com.example.pampam.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    public Page<Product> findList(Pageable pageable);
}
