package com.giunne.questservice.domain.courseState.domain.type;

import com.giunne.commonservice.domain.common.EnumMapperType;

import java.util.Arrays;
import java.util.List;

public enum CourseProgress implements EnumMapperType {
    LOCK("잠금"),
    LOCK_OPEN("잠금풀림"),
    PASSED ("통과"),
    ;

    private final String progress;

    CourseProgress(String description) {
        this.progress = description;
    }

    public static CourseProgress from(String grade) {
        validate(grade);
        return CourseProgress.valueOf(grade.toUpperCase());
    }

    public static boolean isQuestProgress(String progress) {
        List<CourseProgress> roles = Arrays.stream(CourseProgress.values())
                .filter(r -> r.name().equals(progress))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!CourseProgress.isQuestProgress(role.toUpperCase())) {
            throw new IllegalArgumentException("잘못된 QuestProgress 입니다.");
        }
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getTitle() {
        return this.progress;
    }
}
