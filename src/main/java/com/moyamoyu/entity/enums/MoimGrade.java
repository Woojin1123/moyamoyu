package com.moyamoyu.entity.enums;

import lombok.Getter;

@Getter
public enum MoimGrade {
    BASIC(10), // 최대인원
    PREMIUM(30);

    private final int capacity;
    MoimGrade(int capacity) {
        this.capacity = capacity;
    }
}
