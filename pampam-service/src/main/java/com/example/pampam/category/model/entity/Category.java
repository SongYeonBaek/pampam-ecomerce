package com.example.pampam.category.model.entity;

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
}
