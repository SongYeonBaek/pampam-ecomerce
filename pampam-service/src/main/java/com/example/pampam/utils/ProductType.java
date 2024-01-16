package com.example.pampam.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ProductType {
    AGRICULTURE(1, "농산물"),
    SEAFOOD(2, "수산물"),
    FRUIT(3, "과일")
    ;

    private final Integer idx;
    private final String type;
    private static final Map<Integer, String> map = new HashMap<>();

    public static Map<Integer, String> findType() {
        for (ProductType value : ProductType.values()) {
            map.put(value.idx, value.type);
        }
        return map;
    }
}
