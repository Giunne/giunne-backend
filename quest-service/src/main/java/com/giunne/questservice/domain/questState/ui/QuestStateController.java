package com.giunne.questservice.domain.questState.ui;


import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.questState.application.QuestStateService;
import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.CreateQuestStateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "퀘스트 상태 관리", description = "퀘스트 상태 조회 및 저장")
@RestController
@RequestMapping("/v1/api/quest/quest-state")
@RequiredArgsConstructor
public class QuestStateController {

    private final QuestStateService questStateService;

    @Operation(summary = "퀘스트 상태 저장", description = """
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

}
