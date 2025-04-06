package com.giunne.memberservice.domain.push.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.push.application.FcmMessageService;
import com.giunne.memberservice.domain.push.application.dto.request.SaveFcmTokenRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetFcmTokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FCM토큰 관리", description = "FCM토큰 조회 및 저장")
@RestController
@RequestMapping("/v1/api/fcm")
@RequiredArgsConstructor
public class FcmMessageController {

    private final FcmMessageService fcmMessageService;


    @Operation(summary = "FCM토큰 저장", description = """
            ## 기능설명
            * FCM토큰 저장
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    public Response<String> findInProgressQuestByRoadMap(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                                             @RequestBody SaveFcmTokenRequestDto dto
    ) {
        fcmMessageService.saveFcmToken(memberPrincipal, dto);
        return Response.ok("성공");
    }

    @Operation(summary = "FCM토큰 삭제", description = """
            ## 기능설명
            * FCM토큰 삭제
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @DeleteMapping()
    public Response<String> deleteByMemberId(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal
    ) {
        fcmMessageService.deleteByMemberId(memberPrincipal);
        return Response.ok("성공");
    }

    @Operation(summary = "FCM토큰 조회", description = """
            ## 기능설명
            * FCM토큰 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/{memberId}")
    public Response<List<GetFcmTokenResponseDto>> findFcmTokenByMemberId(@PathVariable("memberId") Long memberId
    ) {
        List<GetFcmTokenResponseDto> fcmTokens = fcmMessageService.findByMemberId(memberId);
        return Response.ok(fcmTokens);
    }

//    @Operation(summary = "FCM토큰 삭제", description = """
//            ## 기능설명
//            * FCM토큰 삭제
//            ---
//            """, responses = {
//            @ApiResponse(responseCode = "200", description = "성공")
//    })
//    @DeleteMapping("/delete")
//    public Response<String> deleteByMemberIdAndToken(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
//                                                     @ParameterObject DeleteFcmTokenRequestDto dto
//    ) {
//        fcmMessageService.deleteByMemberIdAndToken(memberPrincipal, dto);
//        return Response.ok("성공");
//    }

}
