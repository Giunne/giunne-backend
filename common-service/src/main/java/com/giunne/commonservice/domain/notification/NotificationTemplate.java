package com.giunne.commonservice.domain.notification;

public enum NotificationTemplate {
    // 사용자용 알림 메시지
    COMMENT_LIK("선생님이 회원님의 댓글을 좋아합니다.") ,
    POST_COMMENT("%s님이 회원님의 게시물에 댓글을 작성했어요."),
    QUEST_COMPLETE("%s 단계의 인증이 완료되었어요."),

    // 선생님용 알림 메시지
    QUEST_CERTIFICATION_REQUEST("%s 학생이 %s 인증을 요청했습니다. 확인해주세요."),
    TEACHER_NOTICE("선생님의 공지사항을 확인해주세요. 중요한 정보가 있을지도 몰라요!")
    ;

    private final String template;

    NotificationTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public String format(Object... args) {
        return String.format(template, args);
    }

}

