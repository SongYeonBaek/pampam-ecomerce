package com.example.pampam.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum Region {
    REGION_1(1, "서울특별시"),
    REGION_2(2, "강원도"),
    REGION_3(3, "충청남도"),
    REGION_4(4, "충청북도"),
    REGION_5(5, "전라남도"),
    REGION_6(6, "전라북도"),
    REGION_7(7, "경상남도"),
    REGION_8(8, "경상북도"),
    REGION_9(9, "제주도"),
    ;

    private final Integer idx;
    private final String region;
    private static final Map<Integer, String> map = new HashMap<>();

    public static Map<Integer, String> findRegion() {
        for (Region value : Region.values()) {
            map.put(value.idx, value.region);
        }
        return map;
    }
}
