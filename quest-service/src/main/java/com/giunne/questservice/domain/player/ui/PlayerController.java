package com.giunne.questservice.domain.player.ui;

import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.UpdatePlayerRequestDto;
import com.giunne.questservice.domain.player.application.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "플레이어 상태 관리", description = "플레이어 상태 조회 및 저장")
@RestController
@RequestMapping("/v1/api/quest/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @Operation(summary = "플레이어 정보 수정", description = """
            ## 기능설명
            * 플레이어 정보 수정합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    }, hidden = true)
    @PutMapping
    public void updatePlayer(@RequestBody UpdatePlayerRequestDto dto) {
        playerService.updatePlayer(dto);
    }
}
