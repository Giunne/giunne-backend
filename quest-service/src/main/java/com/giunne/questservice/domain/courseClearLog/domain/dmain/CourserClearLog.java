package com.giunne.questservice.domain.courseClearLog.domain.dmain;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.quest.api.domain.Quest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_clear_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourserClearLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_clear_log_id")
    private Long id; // 클리어 로그 번호

    @Column(name = "player_no")
    private Long playerNo; // 플레이어 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_no")
    private Course course; // 코스
}
