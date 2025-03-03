package com.giunne.questservice.domain.course.repository;


import com.giunne.commonservice.domain.common.Active;
import com.giunne.questservice.domain.course.application.interfaces.CourseRepository;
import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.course.domain.CoursePath;
import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.course.repository.entity.CoursePathEntity;
import com.giunne.questservice.domain.course.repository.entity.QCourseEntity;
import com.giunne.questservice.domain.course.repository.entity.QCoursePathEntity;
import com.giunne.questservice.domain.course.repository.jpa.JpaCoursePathRepository;
import com.giunne.questservice.domain.course.repository.jpa.JpaCourseRepository;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.member;


@Slf4j
@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaCourseRepository courseRepository;
    private final JpaCoursePathRepository coursePathRepository;
    private final QCourseEntity courseEntity = QCourseEntity.courseEntity;
    private final QCoursePathEntity coursePathEntity = QCoursePathEntity.coursePathEntity;

    @Transactional
    @Override
    public Course insertRootCourse(Course node) {
        // 자기자신 추가
        CourseEntity savedCourse = courseRepository.save(new CourseEntity(node));
        savedCourse.changeParent(List.of(savedCourse.toCourse().getId()));
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

        return savedCourse.toCourse();
    }

    @Override
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
        List<CourseEntity> allCategories = courseRepository.findAll();
        Map<Long, List<CourseEntity>> collect = allCategories.stream()
                .collect(Collectors.toMap(
                        CourseEntity::getId,
                        this::findDirectChildren
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
    public Map<Long, List<Course>> getCoursesByRoadMapId(Long roadMapId) {
        List<CourseEntity> allCategories = courseRepository.findByRoadMap_Id(roadMapId);
        Map<Long, List<CourseEntity>> collect = allCategories.stream()
                .collect(Collectors.toMap(
                        CourseEntity::getId,
                        this::findDirectChildren
                ));

        List<CourseEntity> leafs = findLeaf(
                Course.builder()
                        .roadMap(
                                RoadMap.builder()
                                        .id(roadMapId)
                                        .build()
                        )
                        .build());

        List<Course> roots = findRoot(
                Course.builder()
                        .roadMap(
                                RoadMap.builder()
                                        .id(roadMapId)
                                        .build()
                        )
                        .build())
                .stream().map(i -> {
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

    public List<CourseEntity> findDirectChildren(CourseEntity category) {
        return queryFactory
                .selectFrom(courseEntity)
                .join(coursePathEntity).on(courseEntity.id.eq(coursePathEntity.child.id))
                .where(
                        coursePathEntity.parents.id.eq(category.getId())
                                .and(courseEntity.id.ne(coursePathEntity.parents.id))
                                .and(coursePathEntity.parents.id.in(courseEntity.parent))
                )
                .fetch();

    }

    public List<CourseEntity> findDirectParent(CourseEntity category) {
        return queryFactory
                .selectFrom(courseEntity)
                .join(coursePathEntity).on(courseEntity.id.eq(coursePathEntity.child.id))
                .where(
                        coursePathEntity.child.id.eq(category.getId())
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
