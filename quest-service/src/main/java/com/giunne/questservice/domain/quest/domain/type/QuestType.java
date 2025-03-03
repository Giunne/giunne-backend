package com.giunne.questservice.domain.quest.domain.type;

import com.giunne.commonservice.domain.common.EnumMapperType;

import java.util.Arrays;
import java.util.List;

public enum QuestType  implements EnumMapperType {
    TRAINING("트레이닝"),
    ROAD_MAP("로드맵"),
    RUNNING("조깅"),
    ONE_DAY_MISSION("원데이 미션"),
    ;

    private final String type;

    QuestType(String type) {
        this.type = type;
    }

    public static QuestType from(String grade) {
        validate(grade);
        return QuestType.valueOf(grade.toUpperCase());
    }

    public static boolean isQuestProgress(String progress) {
        List<QuestType> roles = Arrays.stream(QuestType.values())
                .filter(r -> r.name().equals(progress))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!QuestType.isQuestProgress(role.toUpperCase())) {
            throw new IllegalArgumentException("잘못된 QuestType 입니다.");
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
