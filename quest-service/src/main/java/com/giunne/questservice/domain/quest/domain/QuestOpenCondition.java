package com.giunne.questservice.domain.quest.domain;

import com.giunne.questservice.domain.course.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestOpenCondition {
    private Long id;
    private Quest node;
    private Quest parents;
}
