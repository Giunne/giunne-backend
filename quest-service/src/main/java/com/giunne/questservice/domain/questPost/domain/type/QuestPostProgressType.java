package com.giunne.questservice.domain.questPost.domain.type;

import com.giunne.commonservice.domain.common.EnumMapperType;

import java.util.Arrays;
import java.util.List;

public enum QuestPostProgressType implements EnumMapperType {

    UPLOAD ("인증중"),
    FAIL("실패"),
    PASS("패스"),
    ;

    private final String type;

    QuestPostProgressType(String description) {
        this.type = description;
    }

    public static QuestPostProgressType from(String grade) {
        validate(grade);
        return QuestPostProgressType.valueOf(grade.toUpperCase());
    }

    public static boolean isQuestProgress(String progress) {
        List<QuestPostProgressType> roles = Arrays.stream(QuestPostProgressType.values())
                .filter(r -> r.name().equals(progress))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!QuestPostProgressType.isQuestProgress(role.toUpperCase())) {
            throw new IllegalArgumentException("잘못된 QuestPostProgressType 입니다.");
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
