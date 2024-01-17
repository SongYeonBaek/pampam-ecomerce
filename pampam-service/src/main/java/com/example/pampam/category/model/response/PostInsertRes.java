package com.example.pampam.category.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class PostInsertRes {
    @NotNull
    private String productName;
    @NotNull
    private String region;
}
