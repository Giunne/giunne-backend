package com.giunne.questservice.domain.quest.repository.entity;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.quest.domain.QuestOpenCondition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "quest_open_condition")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestOpenConditionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_open_condition_no")
    private Long id; // 코스 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private QuestEntity node;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parents_id", nullable = false)
    private QuestEntity parents;


    public QuestOpenConditionEntity(QuestOpenCondition questOpenCondition) {
        this.id = questOpenCondition.getId();
        this.node = new QuestEntity(questOpenCondition.getNode());
        this.parents = new QuestEntity(questOpenCondition.getParents());
    }

    public QuestOpenCondition toQuestOpenCondition() {
        return QuestOpenCondition.builder()
                .id(id)
                .node(node.toQuest())
                .parents(parents.toQuest())
                .build();
    }
}
