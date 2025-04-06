package com.giunne.memberservice.domain.notice.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
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
@RequestMapping("/v1/api/notice")
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
    public ResponseEntity<String> saveNotice(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                             @RequestBody CreateNoticeRequestDto dto) {

        noticeService.saveNotice(memberPrincipal, dto);
        return ResponseEntity.ok("성공");
    }


    @Operation(summary = "공지 수정", description = """
            ## 기능설명
            * 공지 수정
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PutMapping
    public ResponseEntity<String> updateNotice(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                               @RequestBody UpdateNoticeRequestDto dto) {

        noticeService.updateNotice(memberPrincipal, dto);
        return ResponseEntity.ok("성공");
    }

    @Operation(summary = "공지 다건 조회", description = """
            ## 기능설명
            * 공지 다건 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping()
    public ResponseEntity<PaginationModel<GetNoticeResponseDto>> getNoticeList(@ParameterObject GetNoticeRequestDto dto) {
        PaginationModel<GetNoticeResponseDto> noticeList = noticeService.getNoticeList(dto);
        return ResponseEntity.ok(noticeList);
    }

    @Operation(summary = "공지 상세 조회", description = """
            ## 기능설명
            * 공지 상세 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetNoticeResponseDto> getNoticeList(@PathVariable Long id) {

        GetNoticeResponseDto notice = noticeService.getNotice(id);
        return ResponseEntity.ok(notice);
    }

    @Operation(summary = "공지 삭제", description = """
            ## 기능설명
            * 공지 삭제
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                             @PathVariable Long id) {

        noticeService.deleteById(memberPrincipal, id);
        return ResponseEntity.ok("성공");
    }
}
