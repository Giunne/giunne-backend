package com.giunne.questservice.domain.course.domain.type;

import com.giunne.commonservice.domain.common.EnumMapperType;

import java.util.Arrays;
import java.util.List;

public enum TrainingType implements EnumMapperType {

    CORE("코어"),
    LOWER_BODY("하체"),
    SQUATS("스쿼트"),
    LUNGES("런지"),
    DEADLIFT("데드리프트"),
    RUNNING("조깅"),
    NONE("없음")
    ;

    private final String type;

    TrainingType(String description) {
        this.type = description;
    }

    public static TrainingType from(String grade) {
        validate(grade);
        return TrainingType.valueOf(grade.toUpperCase());
    }

    public static boolean isQuestProgress(String progress) {
        List<TrainingType> roles = Arrays.stream(TrainingType.values())
                .filter(r -> r.name().equals(progress))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!TrainingType.isQuestProgress(role.toUpperCase())) {
            throw new IllegalArgumentException("잘못된 CourseType 입니다.");
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
