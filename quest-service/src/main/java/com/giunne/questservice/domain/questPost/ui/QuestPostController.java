package com.giunne.questservice.domain.questPost.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.quest.application.dto.request.GetUploadQuestRequestDto;
import com.giunne.questservice.domain.quest.application.dto.response.UploadQuestInfoResponseDto;
import com.giunne.questservice.domain.questPost.application.QuestPostService;
import com.giunne.questservice.domain.questPost.application.dto.request.GetPostRequestDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostListResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "퀘스트 게시글 관리", description = "퀘스트 게시글 조회 및 관리")
@RestController
@RequestMapping("/v1/api/quest/post")
@RequiredArgsConstructor
public class QuestPostController {

    private final QuestPostService questPostService;

    @Operation(summary = "게시물 단건 조회", description = """
            ## 기능설명
            * 게시물 단건 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping(value = "/{postId}")
    public Response<GetPostResponseDto> findById(@PathVariable("postId") Long postId) {
        GetPostResponseDto uploadQuests = questPostService.findById(postId);
        return Response.ok(uploadQuests);
    }

    @Operation(summary = "게시물 다건 조회", description = """
            ## 기능설명
            * 게시물 다건 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping
    public Response<GetPostListResponseDto> findMyQuest(
                                                          @ParameterObject GetPostRequestDto dto) {
        GetPostListResponseDto myQuest = questPostService.findMyQuest(dto);
        return Response.ok(myQuest);
    }

}
