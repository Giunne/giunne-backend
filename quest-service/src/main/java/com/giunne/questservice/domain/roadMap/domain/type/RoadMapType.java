package com.giunne.questservice.domain.roadMap.domain.type;

import com.giunne.commonservice.domain.common.EnumMapperType;

import java.util.Arrays;
import java.util.List;

public enum RoadMapType implements EnumMapperType {
    TRAINING("트레이닝"),
    RUNNING("조깅"),
    ;

    private final String type;

    RoadMapType(String description) {
        this.type = description;
    }

    public static RoadMapType from(String grade) {
        validate(grade);
        return RoadMapType.valueOf(grade.toUpperCase());
    }

    public static boolean isQuestProgress(String progress) {
        List<RoadMapType> roles = Arrays.stream(RoadMapType.values())
                .filter(r -> r.name().equals(progress))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!RoadMapType.isQuestProgress(role.toUpperCase())) {
            throw new IllegalArgumentException("잘못된 RoadMapType 입니다.");
        }
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getTitle() {
        return this.type;
    }
    }
