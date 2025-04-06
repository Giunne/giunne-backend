package com.giunne.memberservice.domain.notice.domain;

import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Notice {
    private Long id;
    @Builder.Default
    private String title = "";
    @Builder.Default
    private String content = "";
    private Avatar writer;
    private Recreation recreation;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;


    public void updateNoticeInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
