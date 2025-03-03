package com.giunne.questservice.domain.course.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.course.domain.type.*;
import com.giunne.questservice.domain.roadMap.repository.entity.RoadMapEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;


@Entity
@Table(name = "course")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_no")
    private Long id; // 코스 번호

    @Embedded
    private CourseName courseName; // 코스명

    @Embedded
    private Title title; // 제목

    @Embedded
    private Description description = Description.from(""); // 설명

    @Embedded
    private Color color = Color.from(0, 0, 0); // 색상

    @Embedded
    private DifficultyLevel difficultyLevel = DifficultyLevel.from(1L); // 난이도

    @Embedded
    private IsTeam isTeam = IsTeam.from(false); // 팀전 유무

    @Embedded
    private SortSeq sortSeq = SortSeq.from(1L); //순서번호

    @Embedded
    private Position position = Position.of(0.0, 0.0); // 위치

    @Enumerated(EnumType.STRING)
    @Column(name = "cooperation_type", nullable = false)
    CooperationType cooperationType = CooperationType.SOLO;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_type", nullable = false)
    TrainingType trainingType = TrainingType.NONE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_map_no")
    private RoadMapEntity roadMap;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Embedded
    private Active isActive = Active.from(true);

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Long> parent = new ArrayList<>();

    @Embedded
    private ThumbnailUrl thumbnailUrl;

    @Embedded
    private CurrentApproveCnt currentApproveCnt ;

    @Embedded
    private NeedApproveCnt needApproveCnt;

    @Embedded
    private RewardPoint rewardPoint;

    @Embedded
    private RewardExp rewardExp;

    @Embedded
    private TrainingDescription trainingDescription;

    @Embedded
    private GuideUrl guideUrl;

    /**
     * 부모 ID 목록을 변경합니다.
     * 기존 데이터와 새로운 데이터를 비교하여 필요한 변경만 수행합니다.
     */
    public void changeParent(List<Long> parentIdList) {
        if (parentIdList == null) {
            throw new IllegalArgumentException("부모 ID 목록은 null일 수 없습니다.");
        }

        // 중복 제거 및 정렬을 위한 새로운 리스트 생성
        List<Long> newParentIds = new ArrayList<>(new LinkedHashSet<>(parentIdList));
        Collections.sort(newParentIds);

        // 실제로 변경이 필요할 때만 업데이트 수행
        if (!Objects.equals(this.parent, newParentIds)) {
            if(this.parent != null) {
                this.parent.clear();
            }
            this.parent= newParentIds;
        }
    }

    public Course toCourse() {
        return Course.builder()
                .id(id)
                .courseName(courseName)
                .title(title)
                .description(description)
                .color(color)
                .difficultyLevel(difficultyLevel)
                .isTeam(isTeam)
                .sortSeq(sortSeq)
                .roadMap(roadMap.toRoadMap())
                .position(position)
                .cooperationType(cooperationType)
                .trainingType(trainingType)
                .deadline(deadline)
                .parent(parent)
                .thumbnailUrl(thumbnailUrl)
                .isLeaf(false)
                .isRoot(false)
                .currentApproveCnt(currentApproveCnt)
                .needApproveCnt(needApproveCnt)
                .rewardPoint(rewardPoint)
                .rewardExp(rewardExp)
                .trainingDescription(trainingDescription)
                .guideUrl(guideUrl)
                .build();
    }

    public Course toCourse(boolean isRoot, boolean isLeaf) {
        return Course.builder()
                .id(id)
                .courseName(courseName)
                .id(id)
                .courseName(courseName)
                .title(title)
                .description(description)
                .color(color)
                .difficultyLevel(difficultyLevel)
                .isTeam(isTeam)
                .sortSeq(sortSeq)
                .roadMap(roadMap.toRoadMap())
                .position(position)
                .cooperationType(cooperationType)
                .trainingType(trainingType)
                .isRoot(isRoot)
                .isLeaf(isLeaf)
                .deadline(deadline)
                .parent(parent)
                .thumbnailUrl(thumbnailUrl)
                .currentApproveCnt(currentApproveCnt)
                .needApproveCnt(needApproveCnt)
                .rewardPoint(rewardPoint)
                .rewardExp(rewardExp)
                .trainingDescription(trainingDescription)
                .guideUrl(guideUrl)
                .build();
    }

    public CourseEntity(Course course) {
        this.id = course.getId();
        this.courseName = course.getCourseName();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.color = course.getColor();
        this.difficultyLevel = course.getDifficultyLevel();
        this.isTeam = course.getIsTeam();
        this.sortSeq = course.getSortSeq();
        this.roadMap = new RoadMapEntity(course.getRoadMap());
        this.position = course.getPosition();
        this.cooperationType = course.getCooperationType();
        this.trainingType = course.getTrainingType();
        this.deadline = course.getDeadline();
        this.thumbnailUrl = course.getThumbnailUrl();
        this.parent = course.getParent();
        this.currentApproveCnt = course.getCurrentApproveCnt();
        this.needApproveCnt = course.getNeedApproveCnt();
        this.rewardPoint = course.getRewardPoint();
        this.rewardExp = course.getRewardExp();
        this.trainingDescription = course.getTrainingDescription();
        this.guideUrl = course.getGuideUrl();
    }

}
