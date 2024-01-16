package com.example.pampam.category.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostInsertRes {
    private String productName;
    private String region;
}
