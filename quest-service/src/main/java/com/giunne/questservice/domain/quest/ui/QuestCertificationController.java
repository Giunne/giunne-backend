package com.giunne.questservice.domain.quest.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.quest.application.QuestService;
import com.giunne.questservice.domain.quest.application.dto.request.UpdateQuestInfoRequestDto;
import com.giunne.questservice.domain.questState.application.QuestStateService;
import com.giunne.questservice.domain.questState.application.dto.request.GetConfirmQuestRequestDto;
import com.giunne.questservice.domain.questState.application.dto.request.GetProgressQuestRequestDto;
import com.giunne.questservice.domain.questState.application.dto.response.QuestInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "퀘스트 인증 관리", description = "퀘스트 인증 조회 및 저장")
@RestController
@RequestMapping("/v1/api/quest/certification/student")
@RequiredArgsConstructor
public class QuestCertificationController {

    private final QuestStateService questStateService;

    @Operation(summary = "퀘스트 인증 상황 조회", description = """
            ## 기능설명
            * 퀘스트 인증 상황 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/in-progress")
    public Response<List<QuestInfoResponseDto>> findInProgressQuestByRoadMap(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                                             @ParameterObject GetProgressQuestRequestDto dto
    ) {
        List<QuestInfoResponseDto> inProgressQuestByRoadMap = questStateService.findInProgressQuestByRoadMap(memberPrincipal, dto);
        return Response.ok(inProgressQuestByRoadMap);
    }

    @Operation(summary = "로드맵 히스토리 조회", description = """
            ## 기능설명
            * 로드맵 히스토리 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/history")
    public Response<List<QuestInfoResponseDto>> findConfirmQuestByRoadMap(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                                          @ParameterObject GetConfirmQuestRequestDto dto
    ) {
        List<QuestInfoResponseDto> inProgressQuestByRoadMap = questStateService.findConfirmQuestByRoadMap(memberPrincipal, dto);
        return Response.ok(inProgressQuestByRoadMap);
    }

}
