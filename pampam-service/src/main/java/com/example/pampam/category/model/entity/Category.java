package com.example.pampam.category.model.entity;

import com.example.pampam.category.model.request.PostInsertCategoryReq;
import com.example.pampam.utils.ProductType;
import com.example.pampam.utils.Region;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @Column(nullable = false)
    private String region;
    private String type;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "category")
    private List<CategoryToProduct> categoryList;

    public static Category dtoToEntity(PostInsertCategoryReq categoryReq) {
        return Category.builder()
                .region(Region.findRegion().get(categoryReq.getRegionId()))
                .type(ProductType.findType().get(categoryReq.getTypeId()))
                .build();
    }
}
