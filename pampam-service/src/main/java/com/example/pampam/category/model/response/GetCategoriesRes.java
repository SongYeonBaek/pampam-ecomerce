package com.example.pampam.category.model.response;

import com.example.pampam.category.model.entity.Category;
import lombok.Builder;

@Builder
public class GetCategoriesRes {

    private Long categoryIdx;
    private String categoryType;

    public static GetCategoriesRes buildCategoryRes(Category category) {
        return GetCategoriesRes.builder()
                .categoryIdx(category.getIdx())
                .categoryType(category.getCategoryType())
                .build();
    }
}
