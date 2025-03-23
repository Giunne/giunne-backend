package com.giunne.questservice.domain.quest.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.quest.application.QuestService;
import com.giunne.questservice.domain.quest.application.dto.request.CertificateRequestDto;
import com.giunne.questservice.domain.quest.application.dto.request.GetUploadQuestRequestDto;
import com.giunne.questservice.domain.quest.application.dto.response.UploadQuestInfoResponseDto;
import com.giunne.questservice.domain.questState.application.QuestStateService;
import com.giunne.questservice.domain.questState.application.dto.request.GetConfirmQuestRequestDto;
import com.giunne.questservice.domain.questState.application.dto.request.GetProgressQuestRequestDto;
import com.giunne.questservice.domain.questState.application.dto.response.QuestInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "퀘스트 인증 관리", description = "퀘스트 인증 조회 및 저장")
@RestController
@RequestMapping("/v1/api/quest/certification")
@RequiredArgsConstructor
public class QuestCertificationController {

    private final QuestStateService questStateService;
    private final QuestService questService;

    @Operation(summary = "퀘스트 인증 상황 조회", description = """
            ## 기능설명
            * 퀘스트 인증 상황 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/student/in-progress")
    public Response<List<QuestInfoResponseDto>> findInProgressQuestByRoadMap(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                                             @ParameterObject GetProgressQuestRequestDto dto
    ) {
        List<QuestInfoResponseDto> inProgressQuestByRoadMap = questStateService.findInProgressQuestByRoadMap(memberPrincipal, dto);
        return Response.ok(inProgressQuestByRoadMap);
    }

    @Operation(summary = "로드맵 히스토리 조회", description = """
            ## 기능설명
            * 로드맵 히스토리 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/student/history")
    public Response<List<QuestInfoResponseDto>> findConfirmQuestByRoadMap(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                                          @ParameterObject GetConfirmQuestRequestDto dto
    ) {
        List<QuestInfoResponseDto> inProgressQuestByRoadMap = questStateService.findConfirmQuestByRoadMap(memberPrincipal, dto);
        return Response.ok(inProgressQuestByRoadMap);
    }

    @Operation(summary = "게시글 업로드 퀘스트(로드맵) 인증하기", description = """
            ## 기능설명
            * 게시글 업로드 퀘스트(로드맵) 인증하기
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping(value = "/student",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> certificateQuest(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                             @RequestParam(name = "questId", required = true) Long questId,
                                             @RequestParam(name = "file", required = true) MultipartFile file
    ) {

        questStateService.certificateQuest(memberPrincipal, questId, file);
        return Response.ok("성공");
    }


    @Operation(summary = "학생들이 업로드한 퀘스트 조회(선생님용)", description = """
            ## 기능설명
            * 업로드한 퀘스트 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping(value = "/teacher")
    public Response<List<UploadQuestInfoResponseDto>> findUploadQuests(@ParameterObject GetUploadQuestRequestDto dto) {
        List<UploadQuestInfoResponseDto> uploadQuests = questService.findUploadQuests(dto);
        return Response.ok(uploadQuests);
    }

    @Operation(summary = "채점하기(선생님용)", description = """
            ## 기능설명
            * 채점하기
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping(value = "/teacher/certificate")
    public Response<String> certificate(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                                                  @RequestBody CertificateRequestDto dto
                                                                  ) {
        questStateService.certificate(memberPrincipal, dto);
        return Response.ok("성공");
    }

}
