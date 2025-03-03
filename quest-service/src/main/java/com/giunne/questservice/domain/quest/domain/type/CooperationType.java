package com.giunne.questservice.domain.quest.domain.type;

import com.giunne.commonservice.domain.common.EnumMapperType;

import java.util.Arrays;
import java.util.List;

public enum CooperationType implements EnumMapperType {
    ALL("전체"),
    TEAM("팀"),
    SOLO("개임"),
    ;

    private final String type;

    CooperationType(String type) {
        this.type = type;
    }

    public static CooperationType from(String grade) {
        validate(grade);
        return CooperationType.valueOf(grade.toUpperCase());
    }

    public static boolean isQuestProgress(String progress) {
        List<CooperationType> roles = Arrays.stream(CooperationType.values())
                .filter(r -> r.name().equals(progress))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!CooperationType.isQuestProgress(role.toUpperCase())) {
            throw new IllegalArgumentException("잘못된 CooperationType 입니다.");
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
