package com.giunne.questservice.domain.questPost.ui;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.questPost.application.QuestPostService;
import com.giunne.questservice.domain.questPost.application.dto.request.CommentLikeRequestDto;
import com.giunne.questservice.domain.questPost.application.dto.request.CreateCommentRequestDto;
import com.giunne.questservice.domain.questPost.application.dto.request.GetPostRequestDto;
import com.giunne.questservice.domain.questPost.application.dto.request.UpdateCommentRequestDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetQuestCommentResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostListResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostResponseDto;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestCommentService;
import com.giunne.questservice.domain.questPost.domain.QuestPostComment;
import com.giunne.questservice.domain.questPost.repository.QuestPostCommentRepositoryImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "퀘스트 게시글 관리", description = "퀘스트 게시글 조회 및 관리")
@RestController
@RequestMapping("/v1/api/quest/post")
@RequiredArgsConstructor
public class QuestPostController {

    private final QuestPostService questPostService;
    private final QuestCommentService commentService;
    private final QuestPostCommentRepositoryImpl commentQueryRepository;

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
    public Response<GetPostListResponseDto> findMyQuest(@ParameterObject GetPostRequestDto dto) {
        GetPostListResponseDto myQuest = questPostService.findMyQuest(dto);
        return Response.ok(myQuest);
    }

    @Operation(summary = "댓글 내용 생성", description = """
            ## 기능설명
            * 댓글 내용 생성
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    public Response<Long> createComment(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                        @RequestBody CreateCommentRequestDto dto) {
        QuestPostComment comment = commentService.createComment(memberPrincipal, dto);
        return Response.ok(comment.getId());
    }

    @Operation(summary = "댓글 내용 수정", description = """
            ## 기능설명
            * 댓글 내용 수정
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PutMapping("comment/{commentId}")
    public Response<Long> updateComment(
            @AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody UpdateCommentRequestDto dto) {
        QuestPostComment comment = commentService.updateComment(memberPrincipal, commentId, dto);
        return Response.ok(comment.getId());
    }

    @Operation(summary = "댓글 삭제", description = """
            ## 기능설명
            * 댓글 삭제
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @DeleteMapping("comment/{commentId}")
    public Response<String> deleteComment(
            @AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
            @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(memberPrincipal, commentId);
        return Response.ok("성공");
    }

    @Operation(summary = "댓글 좋아요", description = """
            ## 기능설명
            * 댓글 좋아요
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("comment/like")
    public Response<String> likeComment(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                        @RequestBody CommentLikeRequestDto dto) {
        commentService.likeComment(memberPrincipal, dto);
        return Response.ok("성공");
    }

    @Operation(summary = "댓글 좋아요 취소", description = """
            ## 기능설명
            * 댓글 좋아요 취소
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("comment/unlike")
    public Response<String> unlikeComment( @AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
                                         @RequestBody CommentLikeRequestDto dto) {
        commentService.unlikeComment(memberPrincipal, dto);
        return Response.ok("성공");
    }

    @Operation(summary = "게시물 댓글 조회", description = """
            ## 기능설명
            * 댓글 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/comment/{postId}")
    public Response<PaginationModel<GetQuestCommentResponseDto>> getCommentList(
            @AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal,
            @PathVariable(name = "postId") Long postId,
            @ParameterObject Pageable dto
    ) {

        Long playerId = null;
        if (memberPrincipal != null && memberPrincipal.getPlayerId() != null) {
            playerId = memberPrincipal.getPlayerId();
        }
        PaginationModel<GetQuestCommentResponseDto> commentList = commentQueryRepository.getCommentList(postId,  playerId, dto);
        return Response.ok(commentList);
    }

}
