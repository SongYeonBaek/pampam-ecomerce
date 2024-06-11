package com.example.pampam.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ProductType {
    Vegetable(1L,"채소"),
    Nut(2L, "견과류"),
    Grain(3L,"곡류"),
    Mushroom(4L, "버섯류"),
    Fruit(5L, "과일"),
    SeaFood(6L, "해산물"),
    DriedFish(7L, "건어물"),
    Meat(8L, "정육"),
    MilkProducts(9L, "유제품"),
    Drink(10L, "주류")
    ;

    private final Long idx;
    private final String type;
    private static final Map<Long, String> map = new HashMap<>();

    public static Map<Long, String> findType() {
        for (ProductType value : ProductType.values()) {
            map.put(value.idx, value.type);
        }
        return map;
    }

}