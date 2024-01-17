package com.example.pampam.category.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class PostInsertCategoryReq {
    @NotNull
    private Integer regionId;
    private Integer typeId;
}
