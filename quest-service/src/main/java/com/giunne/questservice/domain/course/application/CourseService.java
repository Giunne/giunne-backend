package com.giunne.questservice.domain.course.application;

import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.questservice.domain.course.application.dto.request.*;
import com.giunne.questservice.domain.course.application.dto.response.*;
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

    public void insertRootCourse(CreateRootCourseRequestDto dto) {

        RoadMap roadMap = roadMapRepository.findById(dto.roadMapId());

        Course course = Course.builder()
                .courseName(CourseName.from(dto.courseName()))
                .roadMap(roadMap)
                .build();

        courseRepository.insertRootCourse(course);
    }

    public void insertCourse(CreateCourseRequestDto dto) {
        RoadMap roadMap = roadMapRepository.findById(dto.roadMapId());
        Course parents = courseRepository.findById(dto.parentsId());
        Course course = Course.builder().courseName(CourseName.from(dto.courseName())).build();
        courseRepository.insertCourse(parents, course);
    }

    public void insertBetween(UpdateCourseRequestDto dto) {
        Course course = Course.builder()
                .courseName(
                        CourseName.from(dto.courseName())
                ).build();
        Course parents = courseRepository.findById(dto.currentId());
        courseRepository.insertBetween(course, parents);
    }


    public void moveWithSubTree(MoveWithSubCourseRequestDto dto) {
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
                                .map(CourseResponseDto::from
                                )
                                .collect(Collectors.toList())
                ));

        return CourseInfoResponseDto.builder()
                .courseInfo(courseResponseMap)
                .build();
    }

    public CourseQuestInfoResponseDto getCoursesByRoadMapId(MemberPrincipal memberPrincipal,GetICourseByRoadmapRequestDto dto) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }
        Map<Long, List<CourseQuestResponseDto>> courses = courseRepository.getCoursesByRoadMapId(memberPrincipal.getPlayerId(), dto.getRoadmapId());

        return CourseQuestInfoResponseDto.builder()
                .courseInfo(courses)
                .build();
    }

    public CourseQuestInfoForTeacherResponseDto getCoursesByRoadMapId2(GetICourseByRoadmapRequestDto dto) {

        Map<Long, List<CourseQuestForTeacherResponseDto>> courses = courseRepository.getCoursesByRoadMapId2(dto.getRoadmapId());

        return CourseQuestInfoForTeacherResponseDto.builder()
                .courseInfo(courses)
                .build();
    }

    public CourseResponseDto updateCourseInfo(UpdateCourseInfoRequestDto dto) {
        Course course = courseRepository.updateCourseInfo(dto);
        return CourseResponseDto.from(course);
    }

}
