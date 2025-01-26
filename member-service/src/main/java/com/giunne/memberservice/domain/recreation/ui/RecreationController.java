package com.giunne.memberservice.domain.recreation.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.recreation.application.RecreationService;
import com.giunne.memberservice.domain.recreation.application.dto.request.CreateRecreationRequestDto;
import com.giunne.memberservice.domain.recreation.application.dto.response.CreateRecreationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "레크레이션 관리", description = "레크레이션 생성 및 조회")
@RestController
@RequestMapping("/v1/api/member/recreation")
@RequiredArgsConstructor
public class RecreationController {

    private final RecreationService recreationService;

    @Operation(summary = "레크레이션 생성", description = """
            ## 기능설명
            * 레크레이션 생성
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    public Response<CreateRecreationResponseDto> creatRecreation(@AuthPrincipal @Parameter(hidden=true)MemberPrincipal memberPrincipal
            , @RequestBody CreateRecreationRequestDto dto) {
        CreateRecreationResponseDto createRecreationResponseDto = recreationService.createRecreation(memberPrincipal, dto);
        return Response.ok(createRecreationResponseDto);
    }

}
