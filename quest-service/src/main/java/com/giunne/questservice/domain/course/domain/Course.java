package com.giunne.questservice.domain.course.domain;

import com.giunne.questservice.domain.course.application.dto.request.UpdateCourseInfoRequestDto;
import com.giunne.questservice.domain.course.domain.type.*;
import com.giunne.questservice.domain.courseState.domain.CourseState;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Course {
    private Long id;
    private CourseName courseName;
    private Title title; // 제목
    @Builder.Default
    private Description description = Description.from(""); // 설명
    private Color color; // 색상
    private DifficultyLevel difficultyLevel; // 난이도
    private IsTeam isTeam;
    private SortSeq sortSeq;
    private RoadMap roadMap;
    private Boolean isLeaf;
    private Boolean isRoot;
    private Position position; // 위치
    private CooperationType cooperationType;
    private TrainingType trainingType;
    private LocalDateTime deadline;
    private List<Long> parent;
    private ThumbnailUrl thumbnailUrl;
    @Builder.Default
    private CurrentApproveCnt currentApproveCnt = CurrentApproveCnt.from(0);
    @Builder.Default
    private NeedApproveCnt needApproveCnt= NeedApproveCnt.from(1);
    @Builder.Default
    private RewardPoint rewardPoint = RewardPoint.from(0L);
    @Builder.Default
    private RewardExp rewardExp = RewardExp.from(0L);
    @Builder.Default
    private TrainingDescription trainingDescription = TrainingDescription.from("");
    @Builder.Default
    private GuideUrl guideUrl = GuideUrl.from("");

    public void changeIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public void changeIsRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public void changeCourseInfo(UpdateCourseInfoRequestDto dto){
        this.description =  Description.from(dto.description());
        this.trainingDescription = TrainingDescription.from(dto.trainingDescription());
        this.rewardExp = RewardExp.from(dto.rewardExp());
        this.rewardPoint = RewardPoint.from(dto.rewardPoint());
        this.guideUrl = GuideUrl.from(dto.guideUrl());
    }


}
