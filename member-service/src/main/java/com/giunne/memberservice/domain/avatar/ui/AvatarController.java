package com.giunne.memberservice.domain.avatar.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.avatar.application.AvatarService;
import com.giunne.memberservice.domain.avatar.application.dto.reqeuest.CreateAvatarRequestDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.CreateAvatarResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "아바타 관리", description = "아바타 생성 및 조회")
@RestController
@RequestMapping("/v1/api/member/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @Operation(summary = "아바타 생성", description = """
            ## 기능설명
            * 아바타 생성
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    public Response<CreateAvatarResponseDto> creatRecreation(@AuthPrincipal @Parameter(hidden=true) MemberPrincipal memberPrincipal
            , @RequestBody CreateAvatarRequestDto dto) {
        CreateAvatarResponseDto createAvatarResponseDto = avatarService.createAvatar(memberPrincipal, dto);
        return Response.ok(createAvatarResponseDto);
    }
}
