package com.giunne.questservice.domain.course.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.course.application.CourseService;
import com.giunne.questservice.domain.course.application.dto.request.*;
import com.giunne.questservice.domain.course.application.dto.response.CourseInfoResponseDto;
import com.giunne.questservice.domain.course.application.dto.response.CourseQuestInfoResponseDto;
import com.giunne.questservice.domain.course.application.dto.response.CourseQuestInfoForTeacherResponseDto;
import com.giunne.questservice.domain.course.application.dto.response.CourseResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Tag(name = "코스 관리", description = "코스 조회 및 저장")
@RestController
@RequestMapping("/v1/api/quest/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "루트 코스 저장", description = """
            ## 기능설명
            * 루트 코스를 저장합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    },
            hidden = true)
    @PostMapping("/root")
    public Response<String> createRoot(@RequestBody CreateRootCourseRequestDto dto) {
        courseService.insertRootCourse(dto);
        return Response.ok("성공");
    }

    @Operation(summary = "코스 하위에 저장", description = """
            ## 기능설명
            * 코스를 다른 코스 하위에 저장합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    },
            hidden = true)
    @PostMapping("/create")
    public Response<String> insertCategory(@RequestBody CreateCourseRequestDto dto) {

        courseService.insertCourse(dto);
        return Response.ok("성공");
    }

    @Operation(summary = "코스 중간에 삽입 생성", description = """
            ## 기능설명
            * 코스를 기존에 존재하는 코스 사이에 삽입 생성합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    },
            hidden = true)
    @PostMapping("/insert-between")
    public Response<String> insertBetween(@RequestBody UpdateCourseRequestDto dto) {
        courseService.insertBetween(dto);
        return Response.ok("성공");
    }

    @Operation(summary = "코스 위치 이동", description = """
            ## 기능설명
            * 코스 위치를 이동시킵니다..
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    },
            hidden = true)
    @PutMapping("/move-with-sub-tree")
    public Response<String> moveWithSubTree(@RequestBody MoveWithSubCourseRequestDto dto) {
        courseService.moveWithSubTree(dto);
        return Response.ok("성공");
    }

    @Operation(summary = "코스 전체 조회", description = """
            ## 기능설명
            * 코스 전체 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    }, hidden = true)
    @GetMapping("/root")
    public Response<CourseInfoResponseDto> getCategories() {
        CourseInfoResponseDto courses = courseService.getCourses();
        return Response.ok(courses);
    }

    @Operation(summary = "로드맵별 코스 조회(학생용)", description = """
            ## 기능설명
            * 학생용 로드맵별 코스 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/road-map")
    public Response<CourseQuestInfoResponseDto> getByRoadMapCategories(@AuthPrincipal @Parameter(hidden=true) MemberPrincipal memberPrincipal,
                                                                       @ParameterObject GetICourseByRoadmapRequestDto dto) {
        CourseQuestInfoResponseDto courses = courseService.getCoursesByRoadMapId(memberPrincipal, dto);
        return Response.ok(courses);
    }

    @Operation(summary = "로드맵별 코스 조회(선생님용)", description = """
            ## 기능설명
            * 선생님용 로드맵별 코스 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/teacher/road-map")
    public Response<CourseQuestInfoForTeacherResponseDto> getByRoadMapCategories2(@ParameterObject GetICourseByRoadmapRequestDto dto) {
        CourseQuestInfoForTeacherResponseDto courses = courseService.getCoursesByRoadMapId2(dto);
        return Response.ok(courses);
    }


    @Operation(summary = "코스정보 수정", description = """
            ## 기능설명
            * 코스정보 수정
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    }, hidden = true)
    @PutMapping
    public Response<CourseResponseDto> updateCourseInfo(@Valid @RequestBody UpdateCourseInfoRequestDto dto) {
        CourseResponseDto courseResponseDto = courseService.updateCourseInfo(dto);
        return Response.ok(courseResponseDto);
    }

}
