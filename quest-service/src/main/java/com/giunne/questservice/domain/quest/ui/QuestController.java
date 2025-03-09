package com.giunne.questservice.domain.quest.ui;

import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.quest.application.QuestService;
import com.giunne.questservice.domain.quest.application.dto.request.UpdateQuestInfoRequestDto;
import com.giunne.questservice.domain.quest.application.dto.response.QuestInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "퀘스트 관리", description = "퀘스트 조회 및 저장")
@RestController
@RequestMapping("/v1/api/quest/quest")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;

    @Operation(summary = "퀘스트 정보 수정", description = """
            ## 기능설명
            * 퀘스트 정보 수정
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PutMapping
    public Response<QuestInfoResponseDto> updateQuestInfo(@Valid @RequestBody UpdateQuestInfoRequestDto dto){
        QuestInfoResponseDto questInfoResponseDto = questService.updateQuestInfo(dto);
        return Response.ok(questInfoResponseDto);
    }

}
