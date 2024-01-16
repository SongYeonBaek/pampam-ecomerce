package com.example.pampam.category.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    private String region;
    private String type;

    @OneToMany(mappedBy = "category")
    private List<CategoryToProduct> categoryList;
}
