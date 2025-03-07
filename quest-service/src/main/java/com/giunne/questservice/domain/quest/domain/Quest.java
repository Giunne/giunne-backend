package com.giunne.questservice.domain.quest.domain;

import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.quest.application.dto.request.UpdateQuestInfoRequestDto;
import com.giunne.questservice.domain.quest.domain.type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Quest {
    private Long id; // 퀘스트 ID
    private Long recreationNo; // 레크레이션 번호
    private Course course;
    @Builder.Default
    private QuestName questName = QuestName.from(""); // 퀘스트명
    @Builder.Default
    private LocalDateTime deadline = LocalDateTime.now(); // 마김일자
    @Builder.Default
    private QuestContent questContent = QuestContent.from(""); // 퀘스트 내용
    @Builder.Default
    private RewardPoint rewardPoint = RewardPoint.from(0L); // 보상 포인트
    @Builder.Default
    private RewardExp rewardExp = RewardExp.from(0L); // 보상 경험치
    @Builder.Default
    private NeedLevel needLevel = NeedLevel.from(0L); // 가능 레벨
    @Builder.Default
    private DifficultyLevel difficultyLevel = DifficultyLevel.from(0L); // 난이도
    @Builder.Default
    private SortSeq sortSeq = SortSeq.from(1L); //순서번호
    @Builder.Default
    private IsTeam isTeam = IsTeam.from(false); // 팀전 여부
    @Builder.Default
    private MaxPlayer maxPlayer = MaxPlayer.from(1);
    @Builder.Default
    private MinPlayer minPlayer = MinPlayer.from(1);
    @Builder.Default
    private QuestType questType = QuestType.ROAD_MAP;
    @Builder.Default
    private CooperationType cooperationType = CooperationType.SOLO;
    @Builder.Default
    private TrainingType trainingType = TrainingType.NONE;
    @Builder.Default
    private GuideUrl guideUrl = GuideUrl.from("");  // 가이드 URL
    @Builder.Default
    private NeedApproveCount needApproveCount = NeedApproveCount.from(1); // 필요 승인 카운트
    @Builder.Default
    private CurrentApproveCount currentApproveCount = CurrentApproveCount.from(0); // 승인 카운트
    @Builder.Default
    private QuestDescription questDescription = QuestDescription.from(""); // 퀘스트 설명
    @Builder.Default
    private TrainingDescription trainingDescription = TrainingDescription.from(""); // 운동 설명

    public void updateQuestDescription(String questDescription) {
        this.questDescription = QuestDescription.from(questDescription);
    }

    public void updateTrainingDescription(String trainingDescription) {
        this.trainingDescription = TrainingDescription.from(trainingDescription);
    }

    public void updateRewardExp(Long rewardExp) {
        this.rewardExp = RewardExp.from(rewardExp);
    }

    public void updateRewardPoint(Long rewardPoint) {
        this.rewardPoint = RewardPoint.from(rewardPoint);
    }

    public void updateGuideUrl(String guideUrl) {
        this.guideUrl = GuideUrl.from(guideUrl);
    }

    public void updateQuestInfo(String questDescription, String trainingDescription, Long rewardExp, Long rewardPoint, String guideUrl) {
        this.questDescription = QuestDescription.from(questDescription);
        this.trainingDescription = TrainingDescription.from(trainingDescription);
        this.rewardExp = RewardExp.from(rewardExp);
        this.rewardPoint = RewardPoint.from(rewardPoint);
        this.guideUrl = GuideUrl.from(guideUrl);
    }

}
