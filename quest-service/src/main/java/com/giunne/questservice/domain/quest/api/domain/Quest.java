package com.giunne.questservice.domain.quest.api.domain;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.questCategory.domain.Category;
import com.giunne.questservice.domain.questClearLog.domain.domain.QuestClearLog;
import com.giunne.questservice.domain.quest.api.domain.type.*;
import com.giunne.questservice.domain.questAttachment.domain.QuestAttachment;
import com.giunne.questservice.domain.station.domain.domain.Station;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "quest")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quest extends BaseEntity {

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
    private DifficultyLevel difficultyLevel; // 난이도

    @Embedded
    private SortSeq sortSeq; //순서번호

    @Embedded
    private isTeam isTeam; // 팀전 여부

    @Embedded
    private MaxUser maxUser = MaxUser.from(1); // 최대 인원수

    @Embedded
    private MinUser minUser = MinUser.from(1); // 최소 인원수

    @Embedded
    private Active isActive = Active.from(true);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private Category category; // 카테고리

    @OneToMany(mappedBy = "quest", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<QuestAttachment> questAttachments = new HashSet<>();  // 첨부파일

    @OneToMany(mappedBy = "quest", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<QuestClearLog> questClearLogs = new HashSet<>();  // 클리어로그

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_no")
    private Station station; // 역
}
