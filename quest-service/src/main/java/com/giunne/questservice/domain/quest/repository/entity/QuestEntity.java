package com.giunne.questservice.domain.quest.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.questservice.domain.quest.domain.type.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "quest")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_no")
    private Long id; // 퀘스트 번호

    @Column(name = "recreation_no")
    private Long recreationNo; // 레크레이션

    @Embedded
    private QuestName questName; // 퀘스트명

    @Embedded
    private QuestContent questContent; // 퀘스트 내용

    @Embedded
    private RewardPoint rewardPoint; // 보상 포인트

    @Embedded
    private RewardExp rewardExp; // 보상 경험치

    @Embedded
    private NeedLevel needLevel; // 가능 레벨

    @Embedded
    private QuestDescription questDescription; // 설명

    @Embedded
    private TrainingDescription trainingDescription; // 설명
    
    @Embedded
    private NeedApproveCount needApproveCount; // 필요 승인 카운트

    @Embedded
    private CurrentApproveCount currentApproveCount; // 승인 카운트

    @Embedded
    private DifficultyLevel difficultyLevel; // 난이도

    @Embedded
    private SortSeq sortSeq; //순서번호

    @Embedded
    private IsTeam isTeam = IsTeam.from(false); // 팀전 여부

    @Embedded
    private MaxPlayer maxPlayer = MaxPlayer.from(1); // 최대 인원수

    @Embedded
    private MinPlayer minPlayer = MinPlayer.from(1); // 최소 인원수

    @Embedded
    private Active isActive = Active.from(true);

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_no", nullable = true)
    private CourseEntity course; // 카테고리

    @Enumerated(EnumType.STRING)
    @Column(name = "quest_type", nullable = false)
    private QuestType questType = QuestType.ROAD_MAP;

    @Enumerated(EnumType.STRING)
    @Column(name = "cooperation_type", nullable = false)
    private CooperationType cooperationType = CooperationType.SOLO;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_type", nullable = false)
    private TrainingType trainingType = TrainingType.NONE;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Embedded
    private GuideUrl guideUrl;  // 가이드 URL

    public QuestEntity(Quest quest) {
        this.id = quest.getId();
        this.recreationNo = quest.getRecreationNo();
        this.questName = quest.getQuestName();
        this.questContent = quest.getQuestContent();
        this.rewardExp = quest.getRewardExp();
        this.rewardPoint= quest.getRewardPoint();
        this.needLevel = quest.getNeedLevel();
        this.difficultyLevel = quest.getDifficultyLevel();
        this.sortSeq = quest.getSortSeq();
        this.isTeam = quest.getIsTeam();
        this.maxPlayer = quest.getMaxPlayer();
        this.minPlayer = quest.getMinPlayer();
        this.questType = quest.getQuestType();
        this.course = new CourseEntity(quest.getCourse());
        this.cooperationType = quest.getCooperationType();
        this.trainingType = quest.getTrainingType();
        this.deadline = quest.getDeadline();
        this.questDescription = quest.getQuestDescription();
        this.guideUrl = quest.getGuideUrl();
        this.needApproveCount = quest.getNeedApproveCount();
        this.currentApproveCount = quest.getCurrentApproveCount();
        this.trainingDescription = quest.getTrainingDescription();
    }

    public Quest toQuest() {
        return Quest.builder()
                .id(id)
                .recreationNo(recreationNo)
                .questName(questName)
                .questContent(questContent)
                .rewardExp(rewardExp)
                .rewardPoint(rewardPoint)
                .difficultyLevel(difficultyLevel)
                .sortSeq(sortSeq)
                .isTeam(isTeam)
                .maxPlayer(maxPlayer)
                .minPlayer(minPlayer)
                .questType(questType)
                .course(course.toCourse())
                .cooperationType(cooperationType)
                .trainingType(trainingType)
                .deadline(deadline)
                .guideUrl(guideUrl)
                .needApproveCount(needApproveCount)
                .currentApproveCount(currentApproveCount)
                .questDescription(questDescription)
                .trainingDescription(trainingDescription)
                .build();
    }

}
