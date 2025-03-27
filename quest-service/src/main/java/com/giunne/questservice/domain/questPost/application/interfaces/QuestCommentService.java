package com.giunne.questservice.domain.questPost.application.interfaces;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.infra.external.domain.member.client.MemberInfoClient;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.player.application.interfaces.PlayerRepository;
import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.player.repository.PlayerRepositoryImpl;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.questPost.application.QuestPostService;
import com.giunne.questservice.domain.questPost.application.dto.request.CommentLikeRequestDto;
import com.giunne.questservice.domain.questPost.application.dto.request.CreateCommentRequestDto;
import com.giunne.questservice.domain.questPost.application.dto.request.UpdateCommentRequestDto;
import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPost.domain.QuestPostComment;
import com.giunne.questservice.domain.questPost.domain.comment.type.QuestPostCommentContent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestCommentService {

    private final QuestPostCommentRepository questCommentRepository;
    private final QuestPostRepository questPostRepository;
    private final QuestPostService postService;
    private final PlayerRepository playerRepository;
    private final QuestPostCommentLikeRepository likeRepository;
    private final MemberInfoClient memberInfoClient;
    private final static long LIKE_EXP = 3L;
    private final static long LIKE_POINT = 3L;


    public QuestPostComment getComment(Long id) {
        return questCommentRepository.findById(id);
    }

    public QuestPostComment createComment(MemberPrincipal memberPrincipal, CreateCommentRequestDto dto) {
        QuestPost post = postService.getPost(dto.postId());

        Player player = playerRepository.findByAvatarId(memberPrincipal.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"))
                .toPlayer();

        QuestPostComment comment = QuestPostComment.builder()
                .content(QuestPostCommentContent.from(dto.content()))
                .player(player)
                .post(post)
                .build();

        return questCommentRepository.save(comment);
    }

    public QuestPostComment updateComment(MemberPrincipal memberPrincipal, Long commentId, UpdateCommentRequestDto dto) {

        QuestPostComment comment = getComment(commentId);
        if (!playerRepository.existsByAvatarId(memberPrincipal.getPlayerId())) {
            throw new IllegalArgumentException("플레이어를 찾을 수 없습니다.");
        }
        if (!(comment.getPlayer().getAvatarId().equals(memberPrincipal.getPlayerId())
                || memberPrincipal.getRole() == MemberRole.ROLE_TEACHER)
        ) {
            throw new IllegalArgumentException("본인 또는 선생님만 수정 가능합니다.");
        }

        comment.updateContent(dto.content());
        return questCommentRepository.save(comment);
    }

    public void deleteComment(MemberPrincipal memberPrincipal, Long commentId) {

        QuestPostComment comment = getComment(commentId);
        if (!playerRepository.existsByAvatarId(memberPrincipal.getPlayerId())) {
            throw new IllegalArgumentException("플레이어를 찾을 수 없습니다.");
        }
        if (!(comment.getPlayer().getAvatarId().equals(memberPrincipal.getPlayerId())
        || memberPrincipal.getRole() == MemberRole.ROLE_TEACHER)
        ) {
            throw new IllegalArgumentException("본인 또는 선생님만 삭제 가능합니다.");
        }
        questCommentRepository.delete(comment);
    }



    public void likeComment(MemberPrincipal memberPrincipal, CommentLikeRequestDto dto) {
        QuestPostComment comment = getComment(dto.postId());
        Player player = playerRepository.findByAvatarId(memberPrincipal.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"))
                .toPlayer();

        if (likeRepository.checkLike(comment, player)) {
            return;
        }

        comment.like(player);
        likeRepository.like(comment, player);

        Long targetAvatarId = comment.getPlayer().getAvatarId();
        // 경험치 증가
        Response<String> memberPointExperiencResponse = memberInfoClient.increaseExperience(targetAvatarId, LIKE_EXP);
        if (memberPointExperiencResponse.code() != HttpStatus.OK.value()) {
            throw new IllegalArgumentException("경험치 증가 실패");
        }

        // 포인트 증가
        Response<String> memberPointIncreaseResponse = memberInfoClient.increasePoint(targetAvatarId, LIKE_POINT);
        if (memberPointIncreaseResponse.code() != HttpStatus.OK.value()) {
            throw new IllegalArgumentException("포인트 증가 실패");
        }
    }

    public void unlikeComment(MemberPrincipal memberPrincipal, CommentLikeRequestDto dto) {
        QuestPostComment comment = getComment(dto.postId());
        Player player = playerRepository.findByAvatarId(memberPrincipal.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"))
                .toPlayer();

        if (likeRepository.checkLike(comment, player)) {
            comment.unlike();
            likeRepository.unlike(comment, player);
        }
    }

}
