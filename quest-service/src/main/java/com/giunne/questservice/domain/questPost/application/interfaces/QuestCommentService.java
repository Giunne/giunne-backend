package com.giunne.questservice.domain.questPost.application.interfaces;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.domain.notification.NotificationTemplate;
import com.giunne.commonservice.domain.notification.NotificationType;
import com.giunne.commonservice.infra.external.domain.member.client.MemberInfoClient;
import com.giunne.commonservice.infra.external.domain.notification.client.NotificationInfoClient;
import com.giunne.commonservice.infra.external.domain.notification.client.dto.request.SendNotificationDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestCommentService {

    private final QuestPostCommentRepository questCommentRepository;
    private final QuestPostRepository questPostRepository;
    private final QuestPostService postService;
    private final PlayerRepository playerRepository;
    private final QuestPostCommentLikeRepository likeRepository;
    private final MemberInfoClient memberInfoClient;
    private final NotificationInfoClient notificationInfoClient;


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

        Long targetId = comment.getPost().getPlayer().getMemberId();

        SendNotificationDto notificationDto = SendNotificationDto.builder()
                .targetId(targetId)
                .senderId(memberPrincipal.getMemberId())
                .title("기운내 프로젝트")
                .content(NotificationTemplate.POST_COMMENT.format(
                        memberPrincipal.getRole() == MemberRole.ROLE_TEACHER ? "선생" : player.getAvatarNickname()
                ))
                .notificationType(NotificationType.POST_COMMENT)
                .build();

        try {
            notificationInfoClient.sendMessage(notificationDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

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

    @Transactional
    public void deleteComment(QuestPostComment comment) {
        questCommentRepository.delete(comment);
    }

    @Transactional
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
        boolean hasExtraExp = false;
        boolean hasExtraPoint = false;
        StringBuilder extraRewardMessage = new StringBuilder();

        // 경험치 증가
        if(!(dto.rewardExp() == null || dto.rewardExp() == 0L)) {
            hasExtraExp = true;
            extraRewardMessage.append(dto.rewardExp()).append("경험치");
            Response<String> memberPointExperiencResponse = memberInfoClient.increaseExperience(targetAvatarId, dto.rewardExp());
            if (memberPointExperiencResponse.code() != HttpStatus.OK.value()) {
                throw new IllegalArgumentException("경험치 증가 실패");
            }

        }

        if(!(dto.rewardPoint() == null || dto.rewardPoint() == 0L)) {
            // 포인트 증가
            hasExtraPoint = true;
            // 경험치도 있다면 쉼표 추가
            if(hasExtraExp) {
                extraRewardMessage.append(", ");
            }
            extraRewardMessage.append(dto.rewardPoint()).append("코인");


            Response<String> memberPointIncreaseResponse = memberInfoClient.increasePoint(targetAvatarId, dto.rewardPoint());
            if (memberPointIncreaseResponse.code() != HttpStatus.OK.value()) {
                throw new IllegalArgumentException("포인트 증가 실패");
            }

        }

        String likTemplate = NotificationTemplate.COMMENT_LIK.getTemplate();
        // 추가 보상이 있는 경우에만 메시지 추가
        if(hasExtraExp || hasExtraPoint) {
            String extraReward = NotificationTemplate.EXTRA_POINT.format(extraRewardMessage.toString());
            likTemplate += extraReward;
        }

        Long targetId = comment.getPlayer().getMemberId();
        SendNotificationDto notificationDto = SendNotificationDto.builder()
                .targetId(targetId)
                .senderId(memberPrincipal.getMemberId())
                .title("기운내 프로젝트")
                .content(likTemplate)
                .notificationType(NotificationType.COMMENT_LIKE)
                .build();

        try {
            notificationInfoClient.sendMessage(notificationDto);
        } catch (Exception e) {
            log.error(e.getMessage());
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

    // 특정 회원의 댓글좋아요 목록 삭제 삭제
    public void unlikeCommentByPlayer(QuestPostComment comment, Player player) {
        if (likeRepository.checkLike(comment, player)) {
            comment.unlike();
            likeRepository.unlike(comment, player);
        }
    }


    public List<QuestPostComment> findByPlayerId(Player player){
        return questCommentRepository.findByPlayerId(player.getId());
    }

}
