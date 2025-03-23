package com.giunne.memberservice.domain.avatar.ui;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.GetAvatarProfileListRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.GetAvatarProfileRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.UpdateExpRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.UpdatePointRequestDto;
import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.avatar.application.AvatarService;
import com.giunne.memberservice.domain.avatar.application.dto.reqeuest.*;
import com.giunne.memberservice.domain.avatar.application.dto.response.AvatarWithWearingItemResponseDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.CreateAvatarResponseDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.memberservice.domain.avatar.application.dto.response.LoginPlayerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @PostMapping("/create")
    public Response<CreateAvatarResponseDto> creatPlayer(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal
            , @RequestBody CreateAvatarRequestDto dto) {
        CreateAvatarResponseDto createAvatarResponseDto = avatarService.creatPlayer(memberPrincipal, dto);
        return Response.ok(createAvatarResponseDto);
    }

    @Operation(summary = "아바타 접속", description = """
            ## 기능설명
            * 아바타 접속
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/login")
    public Response<LoginPlayerResponseDto> loginPlayer(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal
            , @RequestBody LoginPlayerRequestDto dto) {
        LoginPlayerResponseDto loginPlayerResponseDto = avatarService.loginPlayer(memberPrincipal, dto);
        return Response.ok(loginPlayerResponseDto);
    }

    @Operation(summary = "회원 아바타 조회", description = """
            ## 기능설명
            * 회원 아바타 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping
    public Response<PaginationModel<AvatarWithWearingItemResponseDto>> getMyAvatarList(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal
            , @ParameterObject Pageable dto) {
        PaginationModel<AvatarWithWearingItemResponseDto> myAvatarList = avatarService.getMyAvatarList(memberPrincipal, dto);
        return Response.ok(myAvatarList);
    }

    @Operation(summary = "레크레이션의 학생 아바타 조회", description = """
            ## 기능설명
            * 레크레이션의 학생 아바타 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/recreation-students")
    public Response<List<GetMyRecreationAvatarResponseDto>> getMyRecreationStudentList(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal
            , @ParameterObject GetMyRecreationAvatarRequestDto dto) {
        List<GetMyRecreationAvatarResponseDto> myAvatarList = avatarService.getMyRecreationStudentList(memberPrincipal, dto);
        return Response.ok(myAvatarList);
    }

    @Operation(summary = "특정 아바타 프로필 정보 조회", description = """
            ## 기능설명
            * 특정 아바타 정보 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/avatar-profile")
    public Response<GetMyRecreationAvatarResponseDto> getAvatarProfileInfo(@ParameterObject GetAvatarProfileRequestDto dto) {
        GetMyRecreationAvatarResponseDto avatarProfileInfo = avatarService.getAvatarProfileInfo(dto);
        return Response.ok(avatarProfileInfo);
    }

    @Operation(summary = "아바타 프로필 정보 조회", description = """
            ## 기능설명
            * 아바타 정보 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/avatar-profiles")
    public Response<List<GetMyRecreationAvatarResponseDto> > getAvatarProfileListInfo(@ParameterObject GetAvatarProfileListRequestDto dto) {
        List<GetMyRecreationAvatarResponseDto> avatarProfileInfos = avatarService.getAvatarProfileListInfo(dto);
        return Response.ok(avatarProfileInfos);
    }

    @Operation(summary = "경험치 증가", description = """
            ## 기능설명
            * 경험치 증가
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/increase-experience")
    public Response<String> increaseExperience(@RequestBody UpdateExpRequestDto dto) {
        avatarService.increaseExperience(dto);
        return Response.ok("성공");
    }

    @Operation(summary = "포인트 증가", description = """
            ## 기능설명
            * 포인트 증가
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/increase-point")
    public Response<String> increasePoint(@RequestBody UpdatePointRequestDto dto) {
        avatarService.increasePoint(dto);
        return Response.ok("성공");
    }

    @Operation(summary = "포인트 차감", description = """
            ## 기능설명
            * 포인트 차감
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/decrease-point")
    public Response<String> decreasePoint(@RequestBody UpdatePointRequestDto dto) {
        avatarService.decreasePoint(dto);
        return Response.ok("성공");
    }

}
