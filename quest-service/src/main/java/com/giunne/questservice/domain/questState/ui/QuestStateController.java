package com.giunne.questservice.domain.questState.ui;


import com.giunne.commonservice.domain.common.EnumMapper;
import com.giunne.commonservice.domain.common.EnumMapperValue;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.questState.application.QuestStateService;
import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.CreateQuestStateRequestDto;
import com.giunne.questservice.domain.questState.application.dto.request.UpdateQuestStateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Tag(name = "퀘스트 상태 관리", description = "퀘스트 상태 조회 및 저장")
@RestController
@RequestMapping("/v1/api/quest/quest-state")
@RequiredArgsConstructor
public class QuestStateController {

    private final QuestStateService questStateService;
    private final EnumMapper enumMapper;

    @Operation(summary = "퀘스트 상태에 대한 공통코드 조회", description = """
            ## 기능설명
            * 퀘스트 상태에 대한 공통코드를 조회합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/common-code")
    public Response<Map<String, List<EnumMapperValue>>> getLedgerDscList() {
        return Response
                .ok(enumMapper.get(List.of("questProgress")));
    }

    @Operation(summary = "회원별 퀘스트 상태 추가", description = """
            ## 기능설명
            * 퀘스트 상태를 저장합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    Response<String> savePlayerQuestStates(@RequestBody CreateQuestStateRequestDto dto) {
        questStateService.savePlayerQuestStates(dto);
        return Response.ok("성공");
    }


    @Operation(summary = "학생별 퀘스트 진행상태 수정", description = """
            ## 기능설명
            * 학생별 퀘스트 진행상태를 수정합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PutMapping("/quest-progress")
    Response<String> savePlayerQuestStates2(@RequestBody UpdateQuestStateRequestDto dto) {
        questStateService.updateQuestProgress(dto);
        return Response.ok("성공");
    }

}
