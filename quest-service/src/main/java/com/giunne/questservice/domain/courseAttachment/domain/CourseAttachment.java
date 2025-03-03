package com.giunne.questservice.domain.courseAttachment.domain;

import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.courseAttachment.domain.type.SortSeq;
import com.giunne.questservice.domain.questAttachment.domain.type.FileName;
import com.giunne.questservice.domain.questAttachment.domain.type.FileSize;
import com.giunne.questservice.domain.questAttachment.domain.type.FileUrl;
import com.giunne.questservice.domain.questAttachment.domain.type.IsRepresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CourseAttachment {
    private Long id; // 퀘스트파일 번호
    private FileName fileName; // 파일명
    private FileUrl fileUrl; // 파일 URL
    private FileSize fileSize; // 파일 사이즈
    private Course course;
    private IsRepresent isRepresent;
    private SortSeq sortSeq;
}
