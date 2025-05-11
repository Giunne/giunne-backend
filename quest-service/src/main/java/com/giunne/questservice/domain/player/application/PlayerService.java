package com.giunne.questservice.domain.player.application;

import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.DeletePlayerRequestDto;
import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.UpdatePlayerRequestDto;
import com.giunne.questservice.domain.player.application.interfaces.PlayerRepository;
import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestCommentService;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostCommentLikeRepository;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostRepository;
import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPost.domain.QuestPostComment;
import com.giunne.questservice.domain.questPostAttachment.application.interfaces.QuestPostAttachmentRepository;
import com.giunne.questservice.domain.questState.application.interfaces.QuestStateRepository;
import com.giunne.questservice.domain.questState.domain.QuestState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final QuestCommentService questCommentService;
    private final QuestPostCommentLikeRepository questPostCommentLikeRepository;
    private final QuestPostAttachmentRepository questPostAttachmentRepository;
    private final QuestPostRepository questPostRepository;
    private final QuestStateRepository questStateRepository;

    public void updatePlayer(UpdatePlayerRequestDto dto) {

        Optional<PlayerEntity> optionalPlayerEntity = playerRepository.findByAvatarId(dto.avatarId());
        if(optionalPlayerEntity.isEmpty()) {
            return;
        }

        playerRepository.save(new PlayerEntity(
                Player.builder()
                        .id(optionalPlayerEntity.get().getId())
                        .avatarId(dto.avatarId())
                        .userName(dto.userName())
                        .avatarNickname(dto.avatarNickname())
                        .nickname(dto.nickname())
                        .memberId(dto.memberId())
                        .build()
        ));
    }

    @Transactional
    public void deletePlayer(DeletePlayerRequestDto dto) {

        Optional<PlayerEntity> optionalPlayerEntity = playerRepository.findByAvatarId(dto.avatarId());
        if(optionalPlayerEntity.isEmpty()) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }

        Player player = optionalPlayerEntity.get().toPlayer();
        List<QuestPostComment> questPostComments = questCommentService.findByPlayerId(player);

        // 댓글 좋아요 삭제
        questPostComments.forEach(item -> {
            // 회원이 댓글 좋아요한 내역 삭제
            questCommentService.unlikeCommentByPlayer(item, player);
        });
        questPostComments.forEach(item -> {
            // 회원의 댓글에 좋아요한 내역 삭제
            questPostCommentLikeRepository.deleteByComment(item);
        });

        // 댓글 삭제
        questPostComments.forEach(item -> {
            // 회원의 댓글 삭제
            questCommentService.deleteComment(item);
        });


        List<QuestPost> questPosts = questPostRepository.findByPlayer(player.getId());

        // 퀘스트 포스트 첨부파일 삭제
        questPosts.forEach(item -> {
            // 퀘스트 포스트 첨부파일 삭제
            questPostAttachmentRepository.deleteByQuestPostId(item.getId());
        });

        // 퀘스트 포스트 삭제
        questPosts.forEach(item -> {
            // 퀘스트 포스트 첨부파일 삭제
            questPostRepository.deleteById(item.getId());
        });

        List<QuestState> questStateList = questStateRepository.findByPlayer(player.getId());
        // 퀘스트 상태 삭제
        questStateList.forEach(item -> {
            // 퀘스트 상태 삭제
            questStateRepository.deleteById(item.getId());
        });

        // 플레이어 삭제
        playerRepository.deleteByAvatarId(dto.avatarId());
    }

}
