package com.giunne.questservice.domain.course.repository;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.questservice.QuestTestConfiguration;
import com.giunne.questservice.domain.course.application.interfaces.CourseRepository;
import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.course.domain.type.*;
import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.course.repository.jpa.JpaCoursePathRepository;
import com.giunne.questservice.domain.course.repository.jpa.JpaCourseRepository;
import com.giunne.questservice.domain.roadMap.application.interfaces.RoadMapRepository;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = QuestTestConfiguration.class)
@Transactional
class CourseRepositoryImplTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private JpaCourseRepository jpaCourseRepository;
    @Autowired
    private JpaCoursePathRepository jpaCoursePathRepository;
    @Autowired
    private RoadMapRepository roadMapRepository;


    @Test
    @Rollback(false)
    @DisplayName("코스(코어) 초기화 ")
    void initCoreCourse() {
        RoadMap roadMap = roadMapRepository.findById(1L);

        CourseEntity 코어 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("코어"))
                        .title(Title.from("코어"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_코어 = courseRepository.insertRootCourse(코어.toCourse());


        CourseEntity 버드독 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("1.버드독"))
                        .title(Title.from("1.버드독"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.ALL)
                        .trainingType(TrainingType.CORE)
                        .parent(List.of(저장_코어.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][1]버드독.png"))
                        .build()
        );

        Course 저장_버드독 = courseRepository.insertCourse(버드독.toCourse(), 저장_코어);

        CourseEntity 데드버그 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("2.데드버그"))
                        .title(Title.from("2.데드버그"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.ALL)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_버드독.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][2]데드버그.png"))
                        .build()
        );
        Course 저장_데드버그 = courseRepository.insertCourse(데드버그.toCourse(), 저장_버드독);

        CourseEntity 비스트 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("3.비스트"))
                        .title(Title.from("3.비스트"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.ALL)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_데드버그.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][3]비스트.png"))
                        .build()
        );
        Course 저장_비스트 = courseRepository.insertCourse(비스트.toCourse(), 저장_데드버그);

        CourseEntity 플랭크 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("4-a.플랭크"))
                        .title(Title.from("4-a.플랭크"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.ALL)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_비스트.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][4-a]플랭크.png"))
                        .build()
        );
        Course 저장_플랭크 = courseRepository.insertCourse(플랭크.toCourse(), 저장_비스트);

        CourseEntity 하이플랭크 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("4-b.하이플랭크"))
                        .title(Title.from("4-b.하이플랭크"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.ALL)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_비스트.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][4-b]하이플랭크.png"))
                        .build()
        );
        Course 저장_하이플랭크 = courseRepository.insertCourse(하이플랭크.toCourse(), 저장_비스트);

        CourseEntity 플랭크_앤_플랭크 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("4-c.플랭크 앤 플랭크"))
                        .title(Title.from("4-c.플랭크 앤 플랭크"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.ALL)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_비스트.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][4-c]플랭크 앤 플랭크.png"))
                        .build()
        );
        Course 저장_플랭크_앤_플랭크 = courseRepository.insertCourse(플랭크_앤_플랭크.toCourse(), 저장_비스트);

        CourseEntity 플랭크_한발들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("5-a.플랭크 한발들기"))
                        .title(Title.from("플랭크 한발들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_하이플랭크.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][5-a]플랭크 한발들기.png"))
                        .build()
        );
        Course 저장_플랭크_한발들기 = courseRepository.insertCourse(플랭크_한발들기.toCourse(), 저장_하이플랭크);

        CourseEntity 비스트_한발들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("5-b.비스트 한발들기"))
                        .title(Title.from("비스트 한발들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_하이플랭크.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][5-b]비스트 한발들기.png"))
                        .build()
        );
        Course 저장_비스트_한발들기 = courseRepository.insertCourse(비스트_한발들기.toCourse(), 저장_하이플랭크);

        CourseEntity 하이플랭크_한발들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("5-c.하이플랭크 한발들기"))
                        .title(Title.from("하이플랭크 한발들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_하이플랭크.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][5-c]하이플랭크 한발들기.png"))
                        .build()
        );
        Course 저장_하이플랭크_한발들기 = courseRepository.insertCourse(하이플랭크_한발들기.toCourse(), 저장_하이플랭크);

        CourseEntity 플랭크_한손들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("6-a.플랭크 한손들기"))
                        .title(Title.from("6-a.플랭크 한손들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_플랭크_한발들기.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][6-a]플랭크 한손들기.png"))
                        .build()
        );
        Course 저장_플랭크_한손들기 = courseRepository.insertCourse(플랭크_한손들기.toCourse(), 저장_플랭크_한발들기);


        CourseEntity 비스트_한손들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("6-b.비스트 한손들기"))
                        .title(Title.from("6-b.비스트 한손들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_비스트_한발들기.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][6-b]비스트 한손들기.png"))
                        .build()
        );
        Course 저장_비스트_한손들기 = courseRepository.insertCourse(비스트_한손들기.toCourse(), 저장_비스트_한발들기);

        CourseEntity 하이플랭크_한손들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("6-c.하이플랭크 한손들기"))
                        .title(Title.from("6-c.하이플랭크 한손들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_하이플랭크_한발들기.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][6-c]하이플랭크 한손들기.png"))
                        .build()
        );
        Course 저장_하이플랭크_한손들기 = courseRepository.insertCourse(하이플랭크_한손들기.toCourse(), 저장_하이플랭크_한발들기);

        CourseEntity 플랭크_한손_한발_들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("7-a.플랭크 한손 한발 들기"))
                        .title(Title.from("7-a.플랭크 한손 한발 들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_플랭크_한손들기.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][7-a]플랭크 한손 한발 들기.png"))
                        .build()
        );
        Course 저장_플랭크_한손_한발_들기 = courseRepository.insertCourse(플랭크_한손_한발_들기.toCourse(), 저장_플랭크_한손들기);

        CourseEntity 비스트_한손_한발_들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("7-b.비스트 한손 한발 들기"))
                        .title(Title.from("7-b.비스트 한손 한발 들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_비스트_한손들기.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][7-b]비스트 한손 한발 들기.png"))
                        .build()
        );
        Course 저장_비스트_한손_한발_들기 = courseRepository.insertCourse(비스트_한손_한발_들기.toCourse(), 저장_비스트_한손들기);


        CourseEntity 하이플랭크_한손_한발_들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("7-c.하이플랭크 한손 한발 들기"))
                        .title(Title.from("7-c.하이플랭크 한손 한발 들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(저장_하이플랭크_한손들기.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][7-c]하이플랭크 한손 한발 들기.png"))
                        .build()
        );
        Course 저장_하이플랭크_한손_한발_들기 = courseRepository.insertCourse(하이플랭크_한손_한발_들기.toCourse(), 저장_하이플랭크_한손들기);


        CourseEntity 비스트_걷기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("8.비스트 걷기"))
                        .title(Title.from("8.비스트 걷기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(
                                저장_플랭크_한손_한발_들기.getId(),
                                저장_비스트_한손_한발_들기.getId(),
                                저장_하이플랭크_한손_한발_들기.getId()
                        ))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][8]비스트 걷기.png"))
                        .build()
        );
        Course 저장_비스트_걷기 = courseRepository.insertCourse(비스트_걷기.toCourse(), List.of(저장_플랭크_한손_한발_들기,
                저장_비스트_한손_한발_들기,
                저장_하이플랭크_한손_한발_들기
        ));

        CourseEntity 비스트_블럭_걷기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("9.비스트 블럭 걷기"))
                        .title(Title.from("9.비스트 블럭 걷기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(
                                저장_비스트_걷기.getId()
                        ))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][9]비스트 블럭 걷기.png"))
                        .build()
        );
        Course 저장_비스트_블럭_걷기 = courseRepository.insertCourse(비스트_블럭_걷기.toCourse(), 저장_비스트_걷기);


        CourseEntity 언더스위치 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("10.언더스위치"))
                        .title(Title.from("10.언더스위치"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(
                                저장_비스트_블럭_걷기.getId()
                        ))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][10]언더스위치.png"))
                        .build()
        );
        Course 저장_언더스위치 = courseRepository.insertCourse(언더스위치.toCourse(), 저장_비스트_블럭_걷기);

        CourseEntity 사이드킥_스루 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("11.사이드킥 스루"))
                        .title(Title.from("11.사이드킥 스루"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(
                                저장_언더스위치.getId()
                        ))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][11]사이드킥 스루.png"))
                        .build()
        );
        Course 저장_사이드킥_스루 = courseRepository.insertCourse(사이드킥_스루.toCourse(), 저장_언더스위치);

        CourseEntity 사이트플랭크 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("12.사이트플랭크"))
                        .title(Title.from("12.사이트플랭크"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(
                                저장_사이드킥_스루.getId()
                        ))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][12]사이드플랭크.png"))
                        .build()
        );
        Course 저장_사이트플랭크 = courseRepository.insertCourse(사이트플랭크.toCourse(), 저장_사이드킥_스루);


        CourseEntity 사이드_플랭크_굴곡 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("13-a.사이드 플랭크 굴곡"))
                        .title(Title.from("13-a.사이드 플랭크 굴곡"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(
                                저장_사이트플랭크.getId()
                        ))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][13-a]사이드 플랭크 굴곡.png"))
                        .build()
        );
        Course 저장_사이드_플랭크_굴곡 = courseRepository.insertCourse(사이드_플랭크_굴곡.toCourse(), 저장_사이트플랭크);

        CourseEntity 사이드_플랭크_발_들기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("13-b.사이드 플랭크 발 들기"))
                        .title(Title.from("13-b.사이드 플랭크 발 들기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(
                                저장_사이트플랭크.getId()
                        ))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][13-b]사이드 플랭크 발 들기.png"))
                        .build()
        );
        Course 저장_사이드_플랭크_발_들기 = courseRepository.insertCourse(사이드_플랭크_발_들기.toCourse(), 저장_사이트플랭크);

        CourseEntity 사이드_플랭크_회전 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("14.사이드 플랭크 회전"))
                        .title(Title.from("14.사이드 플랭크 회전"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .position(Position.of(0.0, 0.0))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.CORE)
                        .roadMap(roadMap)
                        .parent(List.of(
                                저장_사이드_플랭크_굴곡.getId(),
                                저장_사이드_플랭크_발_들기.getId()
                        ))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[코어][14]사이드 플랭크 회전.png"))
                        .build()
        );
        Course 저장_사이드_플랭크_회전 = courseRepository.insertCourse(사이드_플랭크_회전.toCourse(),
                List.of(
                        저장_사이드_플랭크_굴곡, 저장_사이드_플랭크_발_들기
                )
        );

    }

    @Test
    @Rollback(false)
    @DisplayName("코스(하체) 초기화 ")
    void initLowerBodyCourse() {
        RoadMap roadMap = roadMapRepository.findById(1L);

        CourseEntity 하체0 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("0.하체"))
                        .title(Title.from("0.하체"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.LOWER_BODY)
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_하체0 = courseRepository.insertRootCourse(하체0.toCourse());


        CourseEntity 누워서_고관절_굴곡 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("1-a.누워서 고관절 굴곡"))
                        .title(Title.from("1-a.누워서 고관절 굴곡"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.LOWER_BODY)
                        .parent(List.of(저장_하체0.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[하체][1-a]누워서 고관절 굴곡.png"))
                        .build()
        );
        Course 저장_누워서_고관절_굴곡 = courseRepository.insertCourse(누워서_고관절_굴곡.toCourse(), 저장_하체0);

        CourseEntity 서서_고관절_굴곡 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("1-b.서서 고관절 굴곡"))
                        .title(Title.from("1-b.서서 고관절 굴곡"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.LOWER_BODY)
                        .parent(List.of(저장_하체0.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[하체][1-b]서서 고관절 굴곡.png"))
                        .build()
        );
        Course 저장_서서_고관절_굴곡 = courseRepository.insertCourse(서서_고관절_굴곡.toCourse(), 저장_하체0);

        CourseEntity 엎드려_힙힌지 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("2-a.엎드려 힙힌지"))
                        .title(Title.from("2-a.엎드려 힙힌지"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.LOWER_BODY)
                        .parent(List.of(저장_누워서_고관절_굴곡.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[하체][2-a]엎드려 힙힌지.png"))
                        .build()
        );
        Course 저장_엎드려_힙힌지 = courseRepository.insertCourse(엎드려_힙힌지.toCourse(), 저장_누워서_고관절_굴곡);

        CourseEntity 무릎_꿇고_힙힌지 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("2-b.무릎 꿇고 힙힌지"))
                        .title(Title.from("2-b.무릎 꿇고 힙힌지"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.LOWER_BODY)
                        .parent(List.of(저장_서서_고관절_굴곡.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[하체][2-b]무릎 꿇고 힙힌지.png"))
                        .build()
        );
        Course 저장_무릎_꿇고_힙힌지 = courseRepository.insertCourse(무릎_꿇고_힙힌지.toCourse(), 저장_서서_고관절_굴곡);

        CourseEntity 한발서기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("3.한발서기"))
                        .title(Title.from("3.한발서기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.LOWER_BODY)
                        .parent(List.of(
                                저장_엎드려_힙힌지.getId(),
                                저장_무릎_꿇고_힙힌지.getId()
                        ))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[하체][3]한발서기.png"))
                        .build()
        );
        Course 저장_한발서기 = courseRepository.insertCourse(한발서기.toCourse(), List.of(
                저장_엎드려_힙힌지,
                저장_무릎_꿇고_힙힌지
        ));

        CourseEntity 스쿼트0 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("0.스쿼트"))
                        .title(Title.from("0.스쿼트"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.SQUATS)
                        .parent(List.of(저장_한발서기.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_스쿼트0 = courseRepository.insertCourse(스쿼트0.toCourse(), 저장_한발서기);

        CourseEntity 스쿼트 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("1.스쿼트"))
                        .title(Title.from("1.스쿼트"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.SQUATS)
                        .parent(List.of(저장_스쿼트0.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[스쿼트][1]스쿼트.png"))
                        .build()
        );
        Course 저장_스쿼트 = courseRepository.insertCourse(스쿼트.toCourse(), 저장_스쿼트0);

        CourseEntity 까치발_스쿼트 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("2.까치발 스쿼트"))
                        .title(Title.from("2.까치발 스쿼트"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.SQUATS)
                        .parent(List.of(저장_스쿼트.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[스쿼트][2]까치발 스쿼트.png"))
                        .build()
        );
        Course 저장_까치발_스쿼트 = courseRepository.insertCourse(까치발_스쿼트.toCourse(), 저장_스쿼트);

        CourseEntity 만세_스쿼트 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("3.만세 스쿼트"))
                        .title(Title.from("3.만세 스쿼트"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.SQUATS)
                        .parent(List.of(저장_까치발_스쿼트.getId()))
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[스쿼트][3]만세스쿼트.png"))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_만세_스쿼트 = courseRepository.insertCourse(만세_스쿼트.toCourse(), 저장_까치발_스쿼트);


        CourseEntity 점프_스쿼트 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("4-a.점프 스쿼트"))
                        .title(Title.from("4-a.점프 스쿼트"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.SQUATS)
                        .parent(List.of(저장_만세_스쿼트.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[스쿼트][4-a]점프 스쿼트.png"))
                        .build()
        );
        Course 저장_점프_스쿼트 = courseRepository.insertCourse(점프_스쿼트.toCourse(), 저장_만세_스쿼트);

        CourseEntity 변형_스쿼트 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("4-b.변형 스쿼트"))
                        .title(Title.from("4-b.변형 스쿼트"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.SQUATS)
                        .parent(List.of(저장_만세_스쿼트.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[스쿼트][4-b]변형 스쿼트.png"))
                        .build()
        );
        Course 저장_변형_스쿼트 = courseRepository.insertCourse(변형_스쿼트.toCourse(), 저장_만세_스쿼트);


        CourseEntity 런지0 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("0.런지"))
                        .title(Title.from("0.런지"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.LUNGES)
                        .parent(List.of(저장_스쿼트0.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_런지0 = courseRepository.insertCourse(런지0.toCourse(), 저장_스쿼트0);


        CourseEntity 런지 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("1.런지"))
                        .title(Title.from("1.런지"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.LUNGES)
                        .parent(List.of(저장_런지0.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[런지][1]런지.png"))
                        .build()
        );
        Course 저장_런지 = courseRepository.insertCourse(런지.toCourse(), 저장_런지0);


        CourseEntity 워킹_런지 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("2.워킹 런지"))
                        .title(Title.from("2.워킹 런지"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.LUNGES)
                        .parent(List.of(저장_런지.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[런지][2]워킹 런지.png"))
                        .build()
        );
        Course 워킹_워킹_런지 = courseRepository.insertCourse(런지.toCourse(), 저장_런지);


        CourseEntity 점프런지_모으기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("3.점프런지 모으기"))
                        .title(Title.from("3.점프런지 모으기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.LUNGES)
                        .parent(List.of(워킹_워킹_런지.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[런지][3]점프런지 모으기.png"))
                        .build()
        );
        Course 워킹_점프런지_모으기 = courseRepository.insertCourse(점프런지_모으기.toCourse(), 워킹_워킹_런지);

        CourseEntity 점프런지 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("3.점프런지"))
                        .title(Title.from("3.점프런지"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.LUNGES)
                        .parent(List.of(워킹_점프런지_모으기.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[런지][4]점프런지.png"))
                        .build()
        );
        Course 워킹_점프런지 = courseRepository.insertCourse(점프런지.toCourse(), 워킹_점프런지_모으기);

        CourseEntity 데드리프트0 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("0.데드리프트"))
                        .title(Title.from("0.데드리프트"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.DEADLIFT)
                        .parent(List.of(저장_한발서기.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_데드리프트0 = courseRepository.insertCourse(데드리프트0.toCourse(), 저장_한발서기);

        CourseEntity 힙힌지_움직임 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("1-a.힙힌지 움직임"))
                        .title(Title.from("1-b.힙힌지 움직임"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.DEADLIFT)
                        .parent(List.of(저장_데드리프트0.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[데드][1-a]힙힌지 움직임.png"))
                        .build()
        );
        Course 저장_힙힌지_움직임 = courseRepository.insertCourse(힙힌지_움직임.toCourse(), 저장_데드리프트0);

        CourseEntity 우산_힙힌지 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("1-a.우산 힙힌지"))
                        .title(Title.from("1-b.우산 힙힌지"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.DEADLIFT)
                        .parent(List.of(저장_데드리프트0.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[데드][1-b]우산 힙힌지.png"))
                        .build()
        );
        Course 저장_우산_힙힌지 = courseRepository.insertCourse(우산_힙힌지.toCourse(), 저장_데드리프트0);


        CourseEntity 데드리프트 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("2.데드리프트"))
                        .title(Title.from("2.데드리프트"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(true))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.TEAM)
                        .trainingType(TrainingType.DEADLIFT)
                        .parent(List.of(저장_힙힌지_움직임.getId(), 저장_우산_힙힌지.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[데드][2]데드리프트.png"))
                        .build()
        );
        Course 저장_데드리프트 = courseRepository.insertCourse(데드리프트.toCourse(), List.of(
                저장_힙힌지_움직임, 저장_우산_힙힌지
        ));


        CourseEntity 케틀벨뽑기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("3.케틀벨뽑기"))
                        .title(Title.from("3.케틀벨뽑기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.DEADLIFT)
                        .parent(List.of(저장_데드리프트.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[데드][3]케틀벨뽑기.png"))
                        .build()
        );
        Course 저장_케틀벨뽑기 = courseRepository.insertCourse(케틀벨뽑기.toCourse(), 저장_데드리프트);


        CourseEntity 케틀벨_스윙 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("4.케틀벨 스윙"))
                        .title(Title.from("4.케틀벨 스윙"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.DEADLIFT)
                        .parent(List.of(저장_케틀벨뽑기.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[데드][4]케틀벨 스윙.png"))
                        .build()
        );
        Course 저장_케틀벨_스윙 = courseRepository.insertCourse(케틀벨_스윙.toCourse(), 저장_케틀벨뽑기);

        CourseEntity 한다리_데드_찍기 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("5.한다리 데드 찍기"))
                        .title(Title.from("5.한다리 데드 찍기"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.DEADLIFT)
                        .parent(List.of(저장_케틀벨_스윙.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[데드][5]한다리 데드 찍기.png"))
                        .build()
        );
        Course 저장_한다리_데드_찍기 = courseRepository.insertCourse(한다리_데드_찍기.toCourse(), 저장_케틀벨_스윙);

        CourseEntity 한다리_데드 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("6.한다리 데드"))
                        .title(Title.from("6.한다리 데드"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.DEADLIFT)
                        .parent(List.of(저장_한다리_데드_찍기.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[데드][6]한다리 데드.png"))
                        .build()
        );
        Course 저장_한다리_데드 = courseRepository.insertCourse(한다리_데드.toCourse(), 저장_한다리_데드_찍기);

        CourseEntity 한다리_데드_만세 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("7.한다리 데드 만세"))
                        .title(Title.from("7.한다리 데드 만세"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.DEADLIFT)
                        .parent(List.of(저장_한다리_데드.getId()))
                        .roadMap(roadMap)
                        .thumbnailUrl(ThumbnailUrl.from("web/pictogram/[데드][7]한다리 데드 만세.png"))
                        .build()
        );
        Course 저장_한다리_데드_만세 = courseRepository.insertCourse(한다리_데드_만세.toCourse(), 저장_한다리_데드);


    }

    @Test
    @Rollback(false)
    @DisplayName("코스(조깅) 초기화 ")
    void initRunningCourse() {
        RoadMap roadMap = roadMapRepository.findById(2L);

//        CourseEntity 조깅0 = new CourseEntity(
//                Course.builder()
//                        .courseName(CourseName.from("0.조깅"))
//                        .title(Title.from("0.조깅"))
//                        .deadline(LocalDateTime.now())
//                        .difficultyLevel(DifficultyLevel.from(3L))
//                        .position(Position.of(0.0, 0.0))
//                        .isTeam(IsTeam.from(false))
//                        .sortSeq(SortSeq.from(1L))
//                        .cooperationType(CooperationType.SOLO)
//                        .trainingType(TrainingType.RUNNING)
//                        .roadMap(roadMap)
//                        .build()
//        );
//        Course 저장_조깅0 = courseRepository.insertRootCourse(조깅0.toCourse());


        CourseEntity 주차1 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("1주차"))
                        .title(Title.from("1주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차1 = courseRepository.insertRootCourse(주차1.toCourse());

        CourseEntity 주차2 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("2주차"))
                        .title(Title.from("2주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차1.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차2 = courseRepository.insertCourse(주차2.toCourse(), 저장_주차1);

        CourseEntity 주차3 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("3주차"))
                        .title(Title.from("3주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차2.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차3 = courseRepository.insertCourse(주차3.toCourse(), 저장_주차2);

        CourseEntity 주차4 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("4주차"))
                        .title(Title.from("4주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차3.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차4 = courseRepository.insertCourse(주차4.toCourse(), 저장_주차3);

        CourseEntity 주차5 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("5주차"))
                        .title(Title.from("5주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차4.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차5 = courseRepository.insertCourse(주차5.toCourse(), 저장_주차4);

        CourseEntity 주차6 = new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("6주차"))
                        .title(Title.from("6주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차5.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차6 = courseRepository.insertCourse(주차6.toCourse(), 저장_주차5);

        CourseEntity 주차7= new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("7주차"))
                        .title(Title.from("7주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차6.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차7 = courseRepository.insertCourse(주차7.toCourse(), 저장_주차6);

        CourseEntity 주차8= new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("8주차"))
                        .title(Title.from("8주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차7.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차8 = courseRepository.insertCourse(주차8.toCourse(), 저장_주차7);

        CourseEntity 주차9= new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("9주차"))
                        .title(Title.from("9주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차8.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차9 = courseRepository.insertCourse(주차9.toCourse(), 저장_주차8);

        CourseEntity 주차10= new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("10주차"))
                        .title(Title.from("10주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차9.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차10 = courseRepository.insertCourse(주차10.toCourse(), 저장_주차9);

        CourseEntity 주차11= new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("11주차"))
                        .title(Title.from("11주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차10.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차11 = courseRepository.insertCourse(주차11.toCourse(), 저장_주차10);


        CourseEntity 주차12= new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("12주차"))
                        .title(Title.from("12주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차11.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차12 = courseRepository.insertCourse(주차12.toCourse(), 저장_주차11);

        CourseEntity 주차13= new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("13주차"))
                        .title(Title.from("13주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차12.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차13 = courseRepository.insertCourse(주차13.toCourse(), 저장_주차12);

        CourseEntity 주차14= new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("14주차"))
                        .title(Title.from("14주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차13.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차14 = courseRepository.insertCourse(주차14.toCourse(), 저장_주차13);

        CourseEntity 주차15= new CourseEntity(
                Course.builder()
                        .courseName(CourseName.from("15주차"))
                        .title(Title.from("15주차"))
                        .deadline(LocalDateTime.now())
                        .difficultyLevel(DifficultyLevel.from(3L))
                        .position(Position.of(0.0, 0.0))
                        .isTeam(IsTeam.from(false))
                        .sortSeq(SortSeq.from(1L))
                        .cooperationType(CooperationType.SOLO)
                        .trainingType(TrainingType.RUNNING)
                        .parent(List.of(저장_주차14.getId()))
                        .roadMap(roadMap)
                        .build()
        );
        Course 저장_주차15 = courseRepository.insertCourse(주차15.toCourse(), 저장_주차14);

    }

}