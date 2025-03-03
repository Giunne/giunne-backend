package com.giunne.questservice.domain.questAttachment.domain;

import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.questservice.domain.questAttachment.domain.type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestAttachment {
    private Long id; // 퀘스트파일 번호
    private FileName fileName; // 파일명
    private FileUrl fileUrl; // 파일 URL
    private FileSize fileSize; // 파일 사이즈
    private Quest quest; // 퀘스트
    private IsRepresent isRepresent; // 대표유무
    private SortSeq sortSeq; //순서번호
}
