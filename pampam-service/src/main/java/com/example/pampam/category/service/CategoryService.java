package com.example.pampam.category.service;

import com.example.pampam.category.model.entity.Category;
import com.example.pampam.category.model.response.GetCategoriesRes;
import com.example.pampam.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Object getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<GetCategoriesRes> list = new ArrayList<>();

        for (Category category : categories) {
            list.add(GetCategoriesRes.buildCategoryRes(category));
        }

        return list;
    }

    public Object registerCategory() {

        return null;
    }
}
