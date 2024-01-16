package com.example.pampam.category.repository;

import com.example.pampam.category.model.entity.Category;
import com.example.pampam.category.model.entity.CategoryToProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryToProductRepository extends JpaRepository<CategoryToProduct, Long> {
    List<CategoryToProduct> findALlByCategory(Category category);
}
