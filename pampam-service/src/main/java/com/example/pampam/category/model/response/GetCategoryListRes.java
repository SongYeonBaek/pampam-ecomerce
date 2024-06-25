package com.example.pampam.category.model.response;

import com.example.pampam.category.model.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetCategoryListRes {
    private Long categoryIdx;
    private String categoryType;

    public static GetCategoryListRes buildCategoryList(Category category) {
        return GetCategoryListRes.builder()
                .categoryIdx(category.getIdx())
                .categoryType(category.getCategoryType())
                .build();
    }
}
