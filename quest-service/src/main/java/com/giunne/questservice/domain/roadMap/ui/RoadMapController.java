package com.giunne.questservice.domain.roadMap.ui;

import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.course.application.dto.request.CreateRootCourseRequestDto;
import com.giunne.questservice.domain.roadMap.application.RoadMapService;
import com.giunne.questservice.domain.roadMap.application.dto.request.GetIRoadmapRequestDto;
import com.giunne.questservice.domain.roadMap.application.dto.response.GetIRoadmapResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "로드맵 관리", description = "로드맵 조회 및 저장")
@RestController
@RequestMapping("/v1/api/quest/roadmap")
@RequiredArgsConstructor
public class RoadMapController {

    private final RoadMapService roadMapService;

//    @Operation(summary = "레크레이션의 로드맵 조회", description = """
//            ## 기능설명
//            * 레크레이션의 로드맵을 조회합니다.
//            ---
//            """, responses = {
//            @ApiResponse(responseCode = "200", description = "성공")
//    })
//    @GetMapping
//    public Response<List<GetIRoadmapResponseDto>> roadMapService(@ParameterObject GetIRoadmapRequestDto dto){
//        List<GetIRoadmapResponseDto> getIRoadmapResponseDtos = roadMapService.findByRecreation(dto);
//        return Response.ok(getIRoadmapResponseDtos);
//    }

    @Operation(summary = "모든 로드맵 조회", description = """
            ## 기능설명
            * 모든 로드맵을 조회합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping
    public Response<List<GetIRoadmapResponseDto>> roadMapService(){
        List<GetIRoadmapResponseDto> getIRoadmapResponseDtos = roadMapService.findAll();
        return Response.ok(getIRoadmapResponseDtos);
    }

}
