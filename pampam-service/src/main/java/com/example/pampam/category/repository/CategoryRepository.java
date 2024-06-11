package com.example.pampam.category.repository;

import com.example.pampam.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByCategoryType(String categoryType);
}
