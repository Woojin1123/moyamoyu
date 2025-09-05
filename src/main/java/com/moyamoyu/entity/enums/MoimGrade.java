package com.moyamoyu.entity.enums;

import lombok.Getter;

@Getter
public enum MoimGrade {
    BASIC(10L), // 최대인원
    PREMIUM(30L);

    private final Long capacity;
    MoimGrade(Long capacity) {
        this.capacity = capacity;
    }
}
