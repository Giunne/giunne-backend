package com.giunne.questservice.domain.questPostAttachment.domain;

import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPostAttachment.domain.type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestPostAttachment {
    private Long id;
    @Builder.Default
    private FileName fileName = FileName.from(""); // 파일명
    @Builder.Default
    private FileUrl fileUrl = FileUrl.from(""); // 파일 URL
    @Builder.Default
    private FileSize fileSize = FileSize.from(0L); // 파일 사이즈
    @Builder.Default
    private IsRepresent isRepresent = IsRepresent.from(true); // 대표유무
    @Builder.Default
    private SortSeq sortSeq = SortSeq.from(1L); //순서번호
    private QuestPost questPost;
}
