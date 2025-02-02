package com.giunne.memberservice.domain.recreation.ui;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.recreation.application.RecreationService;
import com.giunne.memberservice.domain.recreation.application.dto.request.CreateRecreationRequestDto;
import com.giunne.memberservice.domain.recreation.application.dto.request.GetRecreationRequestDto;
import com.giunne.memberservice.domain.recreation.application.dto.response.CreateRecreationResponseDto;
import com.giunne.memberservice.domain.recreation.application.dto.response.GetRecreationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "레크레이션 검색", description = """
            ## 기능설명
            * 레크레이션정보를 검색합니다.
            ---
            ## 상세설명
            * 레크레이션명(recreationName)을 이용하여 전국 레크레이션정보를 검색합니다.
            * 레크레이션 코드(recreationCode)을 이용하여 전국 레크레이션정보를 검색합니다.
            * 기수번호(baseNumber)을 이용하여 전국 레크레이션정보를 검색합니다.
            * 선생님명(recreationName)을 이용하여 전국 레크레이션정보를 검색합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping
    public Response<PaginationModel<GetRecreationResponseDto>> findByLike(@AuthPrincipal @Parameter(hidden=true)MemberPrincipal memberPrincipal,
            @ParameterObject GetRecreationRequestDto dto) {
        PaginationModel<GetRecreationResponseDto> recreations = recreationService.getRecreation(memberPrincipal, dto);
        return Response.ok(recreations);
    }

    @Operation(summary = "회원이 가입한 레크레이션 조회", description = """
            ## 기능설명
            * 현재 회원이 가입한 레크레이션 조회합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping
    public Response<PaginationModel<GetRecreationResponseDto>> findMyRecreation(@AuthPrincipal @Parameter(hidden=true)MemberPrincipal memberPrincipal,
                                                                                @ParameterObject Pageable dto
                                                                                ) {
        PaginationModel<GetRecreationResponseDto> recreations = recreationService.getMyRecreation(memberPrincipal, dto);
        return Response.ok(recreations);
    }

}
