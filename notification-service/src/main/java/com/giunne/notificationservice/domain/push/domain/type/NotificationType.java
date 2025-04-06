package com.giunne.notificationservice.domain.push.domain.type;

import com.giunne.commonservice.domain.common.EnumMapperType;

import java.util.Arrays;
import java.util.List;

public enum NotificationType implements EnumMapperType {

    COMMENT_LIKE("댓글 좋아요"),
    POST_COMMENT("게시물 댓글"),
    QUEST_COMPLETE_NOTIFICATION("퀘스트 인증(선생님용)"),
    QUEST_CERTIFICATION_REQUEST("퀘스트 인증(학생용)"),
    TEACHER_NOTICE("공지사항"),
    ;

    private final String type;

    NotificationType(String description) {
        this.type = description;
    }

    public static NotificationType from(String grade) {
        validate(grade);
        return NotificationType.valueOf(grade.toUpperCase());
    }

    public static boolean isNotificationType(String progress) {
        List<NotificationType> roles = Arrays.stream(NotificationType.values())
                .filter(r -> r.name().equals(progress))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!NotificationType.isNotificationType(role.toUpperCase())) {
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
