package com.giunne.questservice.domain.quest.domain;

import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.quest.domain.type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Quest {
    private Long id;
    private Long recreationNo;
    private QuestName questName; // 퀘스트명
    private QuestContent questContent; // 퀘스트 내용
    private RewardPoint rewardPoint; // 보상 포인트
    private RewardExp rewardExp; // 보상 경험치
    private NeedLevel needLevel; // 가능 레벨
    private DifficultyLevel difficultyLevel; // 난이도
    private SortSeq sortSeq; //순서번호
    private isTeam isTeam; // 팀전 여부
    private MaxPlayer maxPlayer;
    private MinPlayer minPlayer;
    private QuestType questType;
    private Course course;
    private CooperationType cooperationType;
    private TrainingType trainingType;
    private LocalDateTime deadline;
}
