package com.example.pampam.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ProductType {
    Vegetable(1,"채소"),
    Nut(2, "견과류"),
    Grain(3,"곡류"),
    Mushroom(4, "버섯류"),
    Fruit(5, "과일"),
    SeaFood(6, "해산물"),
    DriedFish(7, "건어물"),
    Meat(8, "정육"),
    MilkProducts(9, "유제품"),
    Drink(10, "주류")
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