package com.giunne.questservice.domain.questState.domain.type;

import com.giunne.commonservice.domain.common.EnumMapperType;

import java.util.Arrays;
import java.util.List;

public enum QuestProgress implements EnumMapperType {
    LOCK("잠금"),
    LOCK_OPEN("잠금풀림"),
    CHECK("선생님체크"),
    UPLOAD ("인증중"),
    CONFIRM ("통과"),
    ;

    private final String progress;

    QuestProgress(String description) {
        this.progress = description;
    }

    public static QuestProgress from(String grade) {
        validate(grade);
        return QuestProgress.valueOf(grade.toUpperCase());
    }

    public static boolean isQuestProgress(String progress) {
        List<QuestProgress> roles = Arrays.stream(QuestProgress.values())
                .filter(r -> r.name().equals(progress))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!QuestProgress.isQuestProgress(role.toUpperCase())) {
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
