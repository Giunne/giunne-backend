package com.giunne.questservice.domain.course.repository;


import com.giunne.commonservice.domain.common.Active;
import com.giunne.questservice.domain.course.application.dto.request.UpdateCourseInfoRequestDto;
import com.giunne.questservice.domain.course.application.dto.response.*;
import com.giunne.questservice.domain.course.application.interfaces.CourseRepository;
import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.course.domain.CourseParent;
import com.giunne.questservice.domain.course.domain.CoursePath;
import com.giunne.questservice.domain.course.repository.entity.*;
import com.giunne.questservice.domain.course.repository.jpa.JpaCourseParentRepository;
import com.giunne.questservice.domain.course.repository.jpa.JpaCoursePathRepository;
import com.giunne.questservice.domain.course.repository.jpa.JpaCourseRepository;
import com.giunne.questservice.domain.quest.repository.entity.QQuestEntity;
import com.giunne.questservice.domain.questState.repository.entity.QQuestStateEntity;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.querydsl.jpa.JPAExpressions.select;


@Slf4j
@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaCourseRepository courseRepository;
    private final JpaCoursePathRepository coursePathRepository;
    private final QCourseEntity courseEntity = QCourseEntity.courseEntity;
    private final QCoursePathEntity coursePathEntity = QCoursePathEntity.coursePathEntity;
    private final QQuestEntity qQuestEntity = QQuestEntity.questEntity;
    private final QCourseParentEntity qCourseParentEntity = QCourseParentEntity.courseParentEntity;
    private final QQuestStateEntity qQuestStateEntity = QQuestStateEntity.questStateEntity;
    private final JpaCourseParentRepository jpaCourseParentRepository;


    @Transactional
    @Override
    public Course insertRootCourse(Course node) {
        // 자기자신 추가
        CourseEntity savedCourse = courseRepository.save(new CourseEntity(node));
        saveCoursePath(savedCourse.toCourse());

        return savedCourse.toCourse();
    }

    @Transactional
    @Override
    public Course insertCourse(Course node, Course parents) {
        // 자기자신 추가
        CourseEntity savedCourse = courseRepository.save(new CourseEntity(node));

        saveCoursePath(savedCourse.toCourse());
        List<CourseEntity> fetch = getCourseByChild(parents);
        List<CoursePathEntity> list = new ArrayList<>();
        for (CourseEntity course : fetch) {
            list.add(CoursePathEntity.builder()
                    .parents(course)
                    .child(savedCourse)
                    .isActive(Active.from(true))
                    .build());
        }
        coursePathRepository.saveAll(list);

        CourseParent courseParent = CourseParent.builder()
                .node(savedCourse.toCourse())
                .parents(parents)
                .build();
        insertCourseParent(courseParent);

        return savedCourse.toCourse();
    }

    @Override
    @Transactional
    public Course insertCourse(Course node, List<Course> parents) {
        // 자기자신 추가
        CourseEntity savedCourse = courseRepository.save(new CourseEntity(node));

        saveCoursePath(savedCourse.toCourse());
        List<CourseEntity> fetch = getCourseByChild(parents);
        List<CoursePathEntity> list = new ArrayList<>();
        for (CourseEntity course : fetch) {
            list.add(CoursePathEntity.builder()
                    .parents(course)
                    .child(savedCourse)
                    .isActive(Active.from(true))
                    .build());
        }
        coursePathRepository.saveAll(list);

        List<CourseParent> courseParents = new ArrayList<>();
        for (Course parent : parents) {
            courseParents.add(
                    CourseParent.builder()
                            .node(savedCourse.toCourse())
                            .parents(parent)
                            .build()
            );

        }
        insertCourseParent(courseParents);

        return savedCourse.toCourse();
    }

    @Transactional
    @Override
    public void updateQuery(Course node, CoursePath nodePath) {
        queryFactory.update(coursePathEntity)
                .set(coursePathEntity.child, new CourseEntity(node))
                .where(coursePathEntity.id.eq(nodePath.getId()))
                .execute();
    }

    @Transactional
    @Override
    public void insertBetween(Course newNode, Course currentNode) {
        saveCoursePath(newNode);
        List<CourseEntity> fetch = queryFactory.select(coursePathEntity.child)
                .from(coursePathEntity)
                .where(coursePathEntity.id.eq(currentNode.getId()))
                .fetch();

        List<CoursePathEntity> list = new ArrayList<>();
        for (CourseEntity paths : fetch) {
            list.add(
                    CoursePathEntity.builder()
                            .parents(new CourseEntity(newNode))
                            .child(paths)
                            .build()
            );
        }
        updateQuery(new CourseEntity(currentNode), new CourseEntity(newNode));
        coursePathRepository.saveAll(list);
    }


    @Transactional
    @Override
    public void moveWithSubTree(Course target, Course move) {
        deleteByCoursePath(target);
        coursePathRepository.shiftInsertData(move.getId(), target.getId());
    }

    @Override
    public Course findById(Long id) {
        CourseEntity courseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다."));
        return courseEntity.toCourse();
    }

    @Transactional
    @Override
    public void deleteCourse(Course target) {
        deleteCourseById(target);
    }

    @Override
    public Map<Long, List<Course>> getCourses() {
        List<CourseEntity> allCourses = courseRepository.findAll();
        Map<Long, List<CourseEntity>> collect = allCourses.stream()
                .collect(Collectors.toMap(
                        CourseEntity::getId,
                        course -> findDirectChildren(course.getId())
                ));

        List<CourseEntity> leafs = findLeaf(null);

        List<Course> roots = findRoot(null).stream().map(i -> {
                    if (leafs.stream()
                            .map(CourseEntity::getId)
                            .anyMatch(id ->
                                    Objects.equals(id, i.getId())
                            )) {
                        return i.toCourse(true, true);
                    }
                    return i.toCourse(true, false);
                }
        ).toList();

        Map<Long, List<Course>> courses = collect.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(item ->
                                        {

                                            Course course = item.toCourse();
                                            if (leafs.stream()
                                                    .map(CourseEntity::getId)
                                                    .anyMatch(id ->
                                                            Objects.equals(id, item.getId())
                                                    )) {
                                                course.changeIsLeaf(true);
                                            } else if (roots.stream()
                                                    .map(Course::getId)
                                                    .anyMatch(id ->
                                                            Objects.equals(id, item.getId())
                                                    )) {
                                                course.changeIsLeaf(true);
                                            }
                                            return course;
                                        }
                                )
                                .collect(Collectors.toList())
                ));


        courses.put(0L, roots);

        return courses;
    }


    @Override
    public Map<Long, List<CourseQuestResponseDto>> getCoursesByRoadMapId(Long playerId, Long roadMapId) {

        List<Tuple> joinResults = queryFactory
                .select(
                        courseEntity.id,
                        courseEntity.courseName.courseName,
                        courseEntity.title.value,
                        courseEntity.description.value,
                        courseEntity.color,
                        courseEntity.roadMap.id,
                        courseEntity.position,
                        courseEntity.thumbnailUrl.thumbnailUrl,
                        courseEntity.sortSeq.value,
                        qCourseParentEntity.parents.id,
                        qQuestEntity.id,
                        qQuestEntity.questName.value,
                        qQuestEntity.deadline,
                        qQuestEntity.needLevel.value,
                        qQuestEntity.difficultyLevel.value,
                        qQuestEntity.isTeam.value,
                        qQuestEntity.cooperationType,
                        qQuestEntity.trainingType,
                        qQuestEntity.minPlayer.value,
                        qQuestEntity.maxPlayer.value,
                        qQuestEntity.sortSeq.value,
                        qQuestEntity.questType,
                        qQuestEntity.currentApproveCount.value,
                        qQuestEntity.needApproveCount.value,
                        qQuestEntity.rewardPoint.value,
                        qQuestEntity.rewardExp.value,
                        qQuestEntity.trainingDescription.value,
                        qQuestEntity.guideUrl.guideUrl,
                        qQuestEntity.questDescription.value,
                        qQuestStateEntity.id,
                        qQuestStateEntity.player.avatarId,
                        qQuestStateEntity.questProgress,
                        qQuestStateEntity.rewardPoint.value,
                        qQuestStateEntity.rewardExp.value,
                        qQuestStateEntity.starPoint.value,
                        qQuestStateEntity.hasExtraPoints.value
                )
                .from(courseEntity)
                .leftJoin(qCourseParentEntity).on(courseEntity.id.eq(qCourseParentEntity.node.id))
                .leftJoin(qQuestEntity).on(courseEntity.id.eq(qQuestEntity.course.id))
                .leftJoin(qQuestStateEntity).on(qQuestEntity.id.eq(qQuestStateEntity.quest.id))
                .where(courseEntity.roadMap.id.eq(roadMapId)
                        .and(qQuestStateEntity.player.avatarId.eq(playerId))
                )
                .fetch();

        Map<Long, CourseQuestResponseDto> courseMap = new LinkedHashMap<>();
        Map<Long, List<Long>> parentChildMap = new HashMap<>(); // 부모-자식 관계 저장
        Set<Long> allCourseIds = new HashSet<>(); // 모든 코스 ID 저장
        Set<Long> childCourseIds = new HashSet<>(); // 자식으로 등장한 노드 ID 저장

        for (Tuple tuple : joinResults) {
            Long courseId = tuple.get(courseEntity.id);
            Long parentId = tuple.get(qCourseParentEntity.parents.id);

            allCourseIds.add(courseId);

            // courseMap에 데이터 저장
            courseMap.computeIfAbsent(courseId, id -> {
                CourseQuestResponseDto courseDto = new CourseQuestResponseDto();
                courseDto.setId(id);
                courseDto.setCourseName(tuple.get(courseEntity.courseName.courseName));
                courseDto.setTitle(tuple.get(courseEntity.title.value));
                courseDto.setDescription(tuple.get(courseEntity.description.value));
                courseDto.setRoadMapId(tuple.get(courseEntity.roadMap.id));
                courseDto.setPosition(tuple.get(courseEntity.position));
                courseDto.setColor(tuple.get(courseEntity.color));
                courseDto.setThumbnailUrl(tuple.get(courseEntity.thumbnailUrl.thumbnailUrl));
                courseDto.setSortSeq(tuple.get(courseEntity.sortSeq.value));
                courseDto.setParent(new ArrayList<>());  // 부모 리스트 초기화
                return courseDto;
            });

            // 부모 - 자식 관계 저장
            if (parentId != null && !parentId.equals(courseId)) {
                parentChildMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(courseId);
                courseMap.get(courseId).getParent().add(parentId); // 부모 정보 추가
                childCourseIds.add(courseId);
            }

            // 퀘스트 정보 추가
            if (tuple.get(qQuestEntity.id) != null) {
                Long questId = tuple.get(qQuestEntity.id);
                QuestInfoResponseDto questInfo = courseMap.get(courseId).getQuestInfo();

                if (questInfo == null) {
                    questInfo = new QuestInfoResponseDto();
                    questInfo.setId(questId);
                    questInfo.setQuestName(tuple.get(qQuestEntity.questName.value));
                    questInfo.setDeadline(tuple.get(qQuestEntity.deadline));
                    questInfo.setNeedLevel(tuple.get(qQuestEntity.needLevel.value));
                    questInfo.setDifficultyLevel(tuple.get(qQuestEntity.difficultyLevel.value));
                    questInfo.setIsTeam(tuple.get(qQuestEntity.isTeam.value));
                    questInfo.setCooperationType(tuple.get(qQuestEntity.cooperationType));
                    questInfo.setTrainingType(tuple.get(qQuestEntity.trainingType));
                    questInfo.setMaxPlayer(tuple.get(qQuestEntity.maxPlayer.value));
                    questInfo.setMinPlayer(tuple.get(qQuestEntity.minPlayer.value));
                    questInfo.setSortSeq(tuple.get(qQuestEntity.sortSeq.value));
                    questInfo.setQuestType(tuple.get(qQuestEntity.questType));
                    questInfo.setCurrentApproveCount(tuple.get(qQuestEntity.currentApproveCount.value));
                    questInfo.setNeedApproveCount(tuple.get(qQuestEntity.needApproveCount.value));
                    questInfo.setRewardPoint(tuple.get(qQuestEntity.rewardPoint.value));
                    questInfo.setRewardExp(tuple.get(qQuestEntity.rewardExp.value));
                    questInfo.setTrainingDescription(tuple.get(qQuestEntity.trainingDescription.value));
                    questInfo.setGuideUrl(tuple.get(qQuestEntity.guideUrl.guideUrl));
                    questInfo.setQuestDescription(tuple.get(qQuestEntity.questDescription.value));
                    courseMap.get(courseId).setQuestInfo(questInfo);
                }

                // 퀘스트 상태 추가
                if (tuple.get(qQuestStateEntity.id) != null) {
                    QuestStateInfoResponseDto questState = new QuestStateInfoResponseDto();
                    questState.setId(tuple.get(qQuestStateEntity.id));
                    questState.setPlayerId(tuple.get(qQuestStateEntity.player.avatarId));
                    questState.setQuestProgress(tuple.get(qQuestStateEntity.questProgress));
                    questState.setRewardExp(tuple.get(qQuestStateEntity.rewardExp.value));
                    questState.setRewardPoint(tuple.get(qQuestStateEntity.rewardPoint.value));
                    questState.setStarPoint(tuple.get(qQuestStateEntity.starPoint.value));
                    questState.setHasExtraPoints(tuple.get(qQuestStateEntity.hasExtraPoints.value));
                    questInfo.setQuestStateInfo(questState);
                }
            }
        }

        // Root & Leaf 판별
        List<Long> rootIds = allCourseIds.stream()
                .filter(id -> !childCourseIds.contains(id)) // 자식으로 등장하지 않은 노드 == 루트
                .toList();

        List<Long> leafIds = allCourseIds.stream()
                .filter(id -> !parentChildMap.containsKey(id)) // 부모로 등록되지 않은 노드 == 리프
                .toList();

        List<CourseQuestResponseDto> roots = rootIds.stream()
                .map(courseMap::get)
                .filter(Objects::nonNull)
                .toList();

        roots.forEach(i -> {
            i.setIsRoot(true);
            if (leafIds.contains(i.getId())) {
                i.setIsLeaf(true);
            }
        });

        // 부모-자식 관계를 메모리에서 처리
        Map<Long, List<CourseQuestResponseDto>> course = parentChildMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(courseMap::get)
                                .filter(Objects::nonNull)
                                .peek(child -> {
                                    if (leafIds.contains(child.getId())) {
                                        child.changeIsLeaf(true);
                                    }
                                    if (rootIds.contains(child.getId())) {
                                        child.changeIsRoot(true);
                                    }
                                })
                                .collect(Collectors.toList())
                ));

        course.put(0L, roots);
        return course;
    }


    @Override
    public Map<Long, List<CourseQuestForTeacherResponseDto>> getCoursesByRoadMapIdForTeacher(Long roadMapId) {
        List<Tuple> joinResults = queryFactory
                .selectDistinct(
                        courseEntity.id,
                        courseEntity.courseName.courseName,
                        courseEntity.title.value,
                        courseEntity.description.value,
                        courseEntity.color,
                        courseEntity.roadMap.id,
                        courseEntity.position,
                        courseEntity.thumbnailUrl.thumbnailUrl,
                        courseEntity.sortSeq.value,
                        qCourseParentEntity.parents.id,
                        qQuestEntity.id,
                        qQuestEntity.questName.value,
                        qQuestEntity.deadline,
                        qQuestEntity.needLevel.value,
                        qQuestEntity.difficultyLevel.value,
                        qQuestEntity.isTeam.value,
                        qQuestEntity.cooperationType,
                        qQuestEntity.trainingType,
                        qQuestEntity.minPlayer.value,
                        qQuestEntity.maxPlayer.value,
                        qQuestEntity.sortSeq.value,
                        qQuestEntity.questType,
                        qQuestEntity.currentApproveCount.value,
                        qQuestEntity.needApproveCount.value,
                        qQuestEntity.rewardPoint.value,
                        qQuestEntity.rewardExp.value,
                        qQuestEntity.trainingDescription.value,
                        qQuestEntity.guideUrl.guideUrl,
                        qQuestEntity.questDescription.value,
                        qQuestStateEntity.id,
                        qQuestStateEntity.player.avatarId,
                        qQuestStateEntity.questProgress,
                        qQuestStateEntity.rewardPoint.value,
                        qQuestStateEntity.rewardExp.value,
                        qQuestStateEntity.starPoint.value,
                        qQuestStateEntity.hasExtraPoints.value
                )
                .from(courseEntity)
                .leftJoin(qCourseParentEntity).on(courseEntity.id.eq(qCourseParentEntity.node.id))
                .leftJoin(qQuestEntity).on(courseEntity.id.eq(qQuestEntity.course.id))
                .leftJoin(qQuestStateEntity).on(qQuestEntity.id.eq(qQuestStateEntity.quest.id))
                .where(courseEntity.roadMap.id.eq(roadMapId))
                .fetch();

        Map<Long, CourseQuestForTeacherResponseDto> courseMap = new LinkedHashMap<>();
        Map<Long, List<Long>> parentChildMap = new HashMap<>(); // 부모 - 자식 관계 저장
        Set<Long> allCourseIds = new HashSet<>(); // 모든 코스 ID 저장
        Set<Long> childCourseIds = new HashSet<>(); // 자식 노드에 포함된 ID 저장

        for (Tuple tuple : joinResults) {
            Long courseId = tuple.get(courseEntity.id);
            Long parentId = tuple.get(qCourseParentEntity.parents.id);

            allCourseIds.add(courseId); // 모든 코스 ID 추가

            // courseMap에 데이터 저장
            courseMap.computeIfAbsent(courseId, id -> {
                CourseQuestForTeacherResponseDto courseDto = new CourseQuestForTeacherResponseDto();
                courseDto.setId(id);
                courseDto.setCourseName(tuple.get(courseEntity.courseName.courseName));
                courseDto.setTitle(tuple.get(courseEntity.title.value));
                courseDto.setDescription(tuple.get(courseEntity.description.value));
                courseDto.setRoadMapId(tuple.get(courseEntity.roadMap.id));
                courseDto.setPosition(tuple.get(courseEntity.position));
                courseDto.setColor(tuple.get(courseEntity.color));
                courseDto.setThumbnailUrl(tuple.get(courseEntity.thumbnailUrl.thumbnailUrl));
                courseDto.setSortSeq(tuple.get(courseEntity.sortSeq.value));
                courseDto.setParent(new HashSet<>());  // 부모 리스트 초기화
                return courseDto;
            });

            // **부모의 바로 하위 자식만 저장**
            if (parentId != null && !parentId.equals(courseId)) {
                parentChildMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(courseId);
                courseMap.get(courseId).getParent().add(parentId); // 부모 정보 추가
                childCourseIds.add(courseId); // 자식으로 등장한 노드 추가
            }

            // 퀘스트 정보 추가
            if (tuple.get(qQuestEntity.id) != null) {
                Long questId = tuple.get(qQuestEntity.id);
                QuestInfoForTeacherResponseDto questInfo = courseMap.get(courseId).getQuestInfo();

                if (questInfo == null) {
                    questInfo = new QuestInfoForTeacherResponseDto();
                    questInfo.setId(questId);
                    questInfo.setQuestName(tuple.get(qQuestEntity.questName.value));
                    questInfo.setDeadline(tuple.get(qQuestEntity.deadline));
                    questInfo.setNeedLevel(tuple.get(qQuestEntity.needLevel.value));
                    questInfo.setDifficultyLevel(tuple.get(qQuestEntity.difficultyLevel.value));
                    questInfo.setIsTeam(tuple.get(qQuestEntity.isTeam.value));
                    questInfo.setCooperationType(tuple.get(qQuestEntity.cooperationType));
                    questInfo.setTrainingType(tuple.get(qQuestEntity.trainingType));
                    questInfo.setMaxPlayer(tuple.get(qQuestEntity.maxPlayer.value));
                    questInfo.setMinPlayer(tuple.get(qQuestEntity.minPlayer.value));
                    questInfo.setSortSeq(tuple.get(qQuestEntity.sortSeq.value));
                    questInfo.setQuestType(tuple.get(qQuestEntity.questType));
                    questInfo.setCurrentApproveCount(tuple.get(qQuestEntity.currentApproveCount.value));
                    questInfo.setNeedApproveCount(tuple.get(qQuestEntity.needApproveCount.value));
                    questInfo.setRewardPoint(tuple.get(qQuestEntity.rewardPoint.value));
                    questInfo.setRewardExp(tuple.get(qQuestEntity.rewardExp.value));
                    questInfo.setTrainingDescription(tuple.get(qQuestEntity.trainingDescription.value));
                    questInfo.setGuideUrl(tuple.get(qQuestEntity.guideUrl.guideUrl));
                    questInfo.setQuestDescription(tuple.get(qQuestEntity.questDescription.value));
                    questInfo.setQuestStateInfos(new HashSet<>());
                    courseMap.get(courseId).setQuestInfo(questInfo);
                }

                // 퀘스트 상태 추가
                if (tuple.get(qQuestStateEntity.id) != null) {
                    QuestStateInfoForTeacherResponseDto questState = new QuestStateInfoForTeacherResponseDto();
                    questState.setId(tuple.get(qQuestStateEntity.id));
                    questState.setPlayerId(tuple.get(qQuestStateEntity.player.avatarId));
                    questState.setQuestProgress(tuple.get(qQuestStateEntity.questProgress));
                    questState.setRewardExp(tuple.get(qQuestStateEntity.rewardExp.value));
                    questState.setRewardPoint(tuple.get(qQuestStateEntity.rewardPoint.value));
                    questState.setStarPoint(tuple.get(qQuestStateEntity.starPoint.value));
                    questState.setHasExtraPoints(tuple.get(qQuestStateEntity.hasExtraPoints.value));
                    questInfo.getQuestStateInfos().add(questState);
                }
            }
        }

        // Root와 Leaf 판별
        List<Long> rootIds = allCourseIds.stream()
                .filter(id -> !childCourseIds.contains(id)) // 자식으로 등장하지 않은 노드 == 루트
                .toList();

        List<Long> leafIds = allCourseIds.stream()
                .filter(id -> !parentChildMap.containsKey(id)) // 부모로 등록되지 않은 노드 == 리프
                .toList();

        // 루트 정보 추가
        List<CourseQuestForTeacherResponseDto> roots = rootIds.stream()
                .map(courseMap::get)
                .filter(Objects::nonNull)
                .toList();

        roots.forEach(i -> {
            i.setIsRoot(true);
            if (leafIds.contains(i.getId())) {
                i.setIsLeaf(true);
            }
        });

        // 부모-자식 관계를 메모리에서 처리 (바로 하위 자식만 포함)
        Map<Long, List<CourseQuestForTeacherResponseDto>> course = parentChildMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(courseMap::get)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toCollection(LinkedHashSet::new))
                                .stream()
                                .peek(child -> {
                                    if (leafIds.contains(child.getId())) {
                                        child.changeIsLeaf(true);
                                    }
                                    if (rootIds.contains(child.getId())) {
                                        child.changeIsRoot(true);
                                    }
                                })
                                .collect(Collectors.toList())
                ));

        course.put(0L, roots);
        return course;
    }




    @Override
    @Transactional
    public Course updateCourseInfo(UpdateCourseInfoRequestDto dto) {
        CourseEntity courseEntity = courseRepository.findById(dto.id()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 ID입니다.")
        );
        Course course = courseEntity.toCourse();
        course.changeCourseInfo(dto);
        CourseEntity updateCourse = new CourseEntity(course);
        CourseEntity saved = courseRepository.save(updateCourse);
        return saved.toCourse();
    }

    @Override
    public CourseParent insertCourseParent(CourseParent parent) {
        CourseParentEntity save = jpaCourseParentRepository.save(new CourseParentEntity(parent));
        return save.toCourseParent();
    }

    public void insertCourseParentEntity(List<CourseParentEntity> courseParentEntities) {
        jpaCourseParentRepository.saveAll(courseParentEntities);
    }

    @Override
    @Transactional
    public List<CourseParent> insertCourseParent(List<CourseParent> parents) {
        List<CourseParentEntity> list = parents.stream().map(i -> new CourseParentEntity(i)).toList();
        List<CourseParentEntity> save = jpaCourseParentRepository.saveAll(list);
        return save.stream().map(i -> i.toCourseParent()).toList();
    }

    private void saveCoursePath(Course node) {
        coursePathRepository.save(
                CoursePathEntity.builder()
                        .parents(new CourseEntity(node))
                        .child(new CourseEntity(node))
                        .isActive(Active.from(true))
                        .build()
        );
    }

    private void updateQuery(CourseEntity currentNode, CourseEntity newNode) {
        queryFactory.update(coursePathEntity)
                .set(coursePathEntity.child, newNode)
                .where(coursePathEntity.child.id.eq(currentNode.getId()),
                        coursePathEntity.child.id.ne(coursePathEntity.parents.id))
                .execute();
    }

    private List<CourseEntity> getCourseByChild(Course parents) {
        return queryFactory.select(coursePathEntity.parents)
                .from(coursePathEntity)
                .where(coursePathEntity.child.id.eq(parents.getId()))
                .fetch()
                ;
    }

    private List<CourseEntity> getCourseByChild(List<Course> parents) {
        List<Long> idList = parents.stream().map(Course::getId).toList();
        return queryFactory.select(coursePathEntity.parents)
                .from(coursePathEntity)
                .where(coursePathEntity.child.id.in(idList))
                .fetch()
                ;
    }

    public List<CourseEntity> findDirectChildren(Long courseId) {
        return queryFactory
                .selectFrom(courseEntity)
                .join(coursePathEntity).on(courseEntity.id.eq(coursePathEntity.child.id))
                .join(qCourseParentEntity).on(qCourseParentEntity.node.id.eq(courseEntity.id))
                .where(
                        coursePathEntity.parents.id.eq(courseId)
                                .and(courseEntity.id.ne(coursePathEntity.parents.id))
                                .and(coursePathEntity.parents.id.eq(qCourseParentEntity.parents.id))
                )
                .fetch();

    }

    public List<CourseEntity> findDirectChildrenWithQuest(Long courseId) {
        return queryFactory
                .selectFrom(courseEntity)
                .join(coursePathEntity).on(courseEntity.id.eq(coursePathEntity.child.id))
                .join(qCourseParentEntity).on(qCourseParentEntity.node.id.eq(courseEntity.id))
                .where(
                        coursePathEntity.parents.id.eq(courseId)
                                .and(courseEntity.id.ne(coursePathEntity.parents.id))
                                .and(coursePathEntity.parents.id.eq(qCourseParentEntity.parents.id))
                )
                .fetch();

    }


    public List<CourseEntity> findDirectParent(CourseEntity course) {
        return queryFactory
                .selectFrom(courseEntity)
                .join(coursePathEntity).on(courseEntity.id.eq(coursePathEntity.child.id))
                .where(
                        coursePathEntity.child.id.eq(course.getId())
                                .and(courseEntity.id.ne(coursePathEntity.child.id))
                )
                .fetch();
    }

    public List<CourseEntity> findParents(CourseEntity course) {
        return queryFactory
                .selectFrom(courseEntity)
                .join(coursePathEntity).on(courseEntity.id.eq(coursePathEntity.child.id))
                .where(
                        coursePathEntity.child.id.eq(course.getId())
                                .and(courseEntity.id.ne(coursePathEntity.child.id))
                )
                .fetch();
    }


    private void deleteByCoursePath(Course target) {
        QCoursePathEntity q2 = new QCoursePathEntity("q2");
        QCoursePathEntity q3 = new QCoursePathEntity("q3");
        queryFactory
                .delete(coursePathEntity)
                .where(
                        coursePathEntity.child.in(
                                select(q2.child)
                                        .from(q2)
                                        .where(q2.parents.id.eq(target.getId()))
                        ),
                        coursePathEntity.parents.in(
                                select(q3.parents)
                                        .from(q3)
                                        .where(q3.child.id.eq(target.getId()),
                                                q3.parents.ne(q3.child))
                        )
                ).execute();
    }

    public void deleteCourseById(Course target) {
        courseRepository.deleteById(target.getId());
    }


    public List<CourseEntity> findRoot(Course course) {
        return queryFactory
                .selectFrom(courseEntity)
                .where(
                        JPAExpressions.selectFrom(coursePathEntity)
                                .where(
                                        coursePathEntity.child.id.eq(courseEntity.id)
                                                .and(coursePathEntity.parents.id.ne(courseEntity.id))
                                ).notExists()
                                .and(roadMapEq(course))
                )
                .fetch();
    }

    public List<CourseEntity> findLeaf(Course course) {

        return queryFactory
                .selectFrom(courseEntity)
                .where(
                        JPAExpressions.selectFrom(coursePathEntity)
                                .where(
                                        coursePathEntity.parents.id.eq(courseEntity.id)
                                                .and(coursePathEntity.child.id.ne(courseEntity.id))
                                ).notExists()
                                .and(roadMapEq(course))
                )

                .fetch();
    }

    private BooleanExpression roadMapEq(Course course) {
        if (course == null || course.getRoadMap().getId() == null) {
            return null;
        }
        return courseEntity.roadMap.id.eq(course.getRoadMap().getId());
    }
}
