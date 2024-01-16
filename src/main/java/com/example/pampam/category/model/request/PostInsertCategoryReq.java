package com.example.pampam.category.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostInsertCategoryReq {
    private Integer regionId;
    private Integer typeId;
}
