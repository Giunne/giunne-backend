package com.giunne.memberservice.domain.notice.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.notice.application.NoticeService;
import com.giunne.memberservice.domain.notice.application.dto.request.CreateNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.request.GetNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.request.UpdateNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.response.GetNoticeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공지 관리", description = "공지 조회 및 저장")
@RestController
@RequestMapping("/v1/api/member/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지 저장", description = """
            ## 기능설명
            * 공지 저장
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    public Response<String> saveNotice(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                       @RequestBody CreateNoticeRequestDto dto) {

        noticeService.saveNotice(memberPrincipal, dto);
        return Response.ok("성공");
    }


    @Operation(summary = "공지 수정", description = """
            ## 기능설명
            * 공지 수정
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PutMapping
    public Response<String> updateNotice(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                               @RequestBody UpdateNoticeRequestDto dto) {

        noticeService.updateNotice(memberPrincipal, dto);
        return Response.ok("성공");
    }

    @Operation(summary = "공지 다건 조회", description = """
            ## 기능설명
            * 공지 다건 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping()
    public Response<PaginationModel<GetNoticeResponseDto>> getNoticeList(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                                         @ParameterObject GetNoticeRequestDto dto) {
        PaginationModel<GetNoticeResponseDto> noticeList = noticeService.getNoticeList(memberPrincipal, dto);
        return Response.ok(noticeList);
    }

    @Operation(summary = "공지 상세 조회", description = """
            ## 기능설명
            * 공지 상세 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/{id}")
    public Response<GetNoticeResponseDto> getNoticeList(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                        @PathVariable Long id) {
        GetNoticeResponseDto notice = noticeService.getNotice(memberPrincipal, id);
        return Response.ok(notice);
    }

    @Operation(summary = "공지 삭제", description = """
            ## 기능설명
            * 공지 삭제
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @DeleteMapping("/{id}")
    public Response<String> deleteById(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                             @PathVariable Long id) {

        noticeService.deleteById(memberPrincipal, id);
        return Response.ok("성공");
    }

    @Operation(summary = "공지 읽음 처리", description = """
            ## 기능설명
            * 공지 읽음 처리
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PutMapping("/read/{id}")
    public Response<String> readNotice(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                        @PathVariable Long id) {

        noticeService.readNotice(memberPrincipal, id);
        return Response.ok("성공");
    }
}
