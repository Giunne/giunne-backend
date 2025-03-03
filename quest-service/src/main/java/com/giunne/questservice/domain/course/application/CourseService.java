package com.giunne.questservice.domain.course.application;

import com.giunne.questservice.domain.course.application.dto.request.*;
import com.giunne.questservice.domain.course.application.dto.response.CourseInfoResponseDto;
import com.giunne.questservice.domain.course.application.dto.response.CourseResponseDto;
import com.giunne.questservice.domain.course.application.interfaces.CourseRepository;
import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.course.domain.type.CourseName;
import com.giunne.questservice.domain.roadMap.application.interfaces.RoadMapRepository;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final RoadMapRepository roadMapRepository;

    public void insertRootCourse(CreateRootCourseRequestDto dto){

        RoadMap roadMap = roadMapRepository.findById(dto.roadMapId());

        Course course = Course.builder()
                .courseName(CourseName.from(dto.courseName()))
                .roadMap(roadMap)
                .build();

        courseRepository.insertRootCourse(course);
    }

    public void insertCourse(CreateCourseRequestDto dto){
        RoadMap roadMap = roadMapRepository.findById(dto.roadMapId());
        Course parents = courseRepository.findById(dto.parentsId());
        Course course = Course.builder().courseName(CourseName.from(dto.courseName())).build();
        courseRepository.insertCourse(parents, course);
    }

    public void insertBetween(UpdateCourseRequestDto dto){
        Course course = Course.builder()
                .courseName(
                        CourseName.from(dto.courseName())
                ).build();
        Course parents = courseRepository.findById(dto.currentId());
        courseRepository.insertBetween(course, parents);
    }


    public void moveWithSubTree(MoveWithSubCourseRequestDto dto){
        Course current = courseRepository.findById(dto.currentId());
        Course move = courseRepository.findById(dto.moveId());
        courseRepository.moveWithSubTree(current, move);
    }

    public CourseInfoResponseDto getCourses() {

        Map<Long, List<Course>> categories = courseRepository.getCourses();

        Map<Long, List<CourseResponseDto>> courseResponseMap = categories.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(course ->
                                        CourseResponseDto.builder()
                                                .id(course.getId())
                                                .courseName(course.getCourseName().getCourseName())
                                                .title(course.getTitle().getValue())
                                                .description(course.getDescription().getValue())
                                                .color(course.getColor())
                                                .difficultyLevel(course.getDifficultyLevel().getValue())
                                                .isTeam(course.getIsTeam().isValue())
                                                .cooperationType(course.getCooperationType())
                                                .trainingType(course.getTrainingType())
                                                .deadline(course.getDeadline())
                                                .sortSeq(course.getSortSeq().getValue())
                                                .roadMapId(course.getRoadMap().getId())
                                                .position(course.getPosition())
                                                .isRoot(course.getIsRoot())
                                                .isLeaf(course.getIsLeaf())
                                                .thumbnailUrl(course.getThumbnailUrl() != null ? course.getThumbnailUrl().getThumbnailUrl() : null)
                                                .parent(course.getParent())
                                                .build()
                                        )
                                .collect(Collectors.toList())
                ));

        return CourseInfoResponseDto.builder()
                .courseInfo(courseResponseMap)
                .build();
    }

    public CourseInfoResponseDto getCoursesByRoadMapId(GetICourseByRoadmapRequestDto dto) {

        Map<Long, List<Course>> categories = courseRepository.getCoursesByRoadMapId(dto.getRoadmapId());

        Map<Long, List<CourseResponseDto>> courseResponseMap = categories.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(course ->
                                        CourseResponseDto.builder()
                                                .id(course.getId())
                                                .courseName(course.getCourseName().getCourseName())
                                                .title(course.getTitle().getValue())
                                                .description(course.getDescription().getValue())
                                                .color(course.getColor())
                                                .difficultyLevel(course.getDifficultyLevel().getValue())
                                                .isTeam(course.getIsTeam().isValue())
                                                .cooperationType(course.getCooperationType())
                                                .trainingType(course.getTrainingType())
                                                .deadline(course.getDeadline())
                                                .sortSeq(course.getSortSeq().getValue())
                                                .roadMapId(course.getRoadMap().getId())
                                                .position(course.getPosition())
                                                .isRoot(course.getIsRoot())
                                                .isLeaf(course.getIsLeaf())
                                                .thumbnailUrl(course.getThumbnailUrl() != null ? course.getThumbnailUrl().getThumbnailUrl() : null)
                                                .parent(course.getParent())
                                                .build()
                                )
                                .collect(Collectors.toList())
                ));

        return CourseInfoResponseDto.builder()
                .courseInfo(courseResponseMap)
                .build();
    }

}
