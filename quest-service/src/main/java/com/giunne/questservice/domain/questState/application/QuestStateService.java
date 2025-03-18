package com.giunne.questservice.domain.questState.application;


import com.giunne.commonservice.infra.external.domain.synology.client.SynologyInfoClient;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.util.FileUtils;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostRepository;
import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostProgressType;
import com.giunne.questservice.domain.questPostAttachment.application.interfaces.QuestPostAttachmentRepository;
import com.giunne.questservice.domain.questPostAttachment.domain.QuestPostAttachment;
import com.giunne.questservice.domain.questPostAttachment.domain.type.FileName;
import com.giunne.questservice.domain.questPostAttachment.domain.type.FileSize;
import com.giunne.questservice.domain.questPostAttachment.domain.type.FileUrl;
import com.giunne.questservice.domain.questState.application.dto.request.GetConfirmQuestRequestDto;
import com.giunne.questservice.domain.questState.application.dto.request.GetProgressQuestRequestDto;
import com.giunne.questservice.domain.questState.application.dto.request.UpdateQuestStateRequestDto;
import com.giunne.questservice.domain.player.application.interfaces.PlayerRepository;
import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.quest.application.interfaces.QuestRepository;
import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.CreateQuestStateRequestDto;
import com.giunne.questservice.domain.questState.application.dto.response.QuestInfoResponseDto;
import com.giunne.questservice.domain.questState.application.interfaces.QuestStateRepository;
import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QuestStateService {

    @Value("${app.apiUrl.synology-service}")
    private String synologyApiUrl;

    private final QuestStateRepository questStateRepository;
    private final QuestRepository questRepository;
    private final PlayerRepository playerRepository;
    private final QuestPostAttachmentRepository questPostAttachmentRepository;
    private final QuestPostRepository questPostRepository;
    private final SynologyInfoClient synologyInfoClient;

    private final String CATEGORY = "certificate";

    public void savePlayerQuestStates(CreateQuestStateRequestDto dto) {
        List<Quest> quests = questRepository.findByRoadMap(dto.roadMapId());

        Player playerEntity;
        Optional<PlayerEntity> optionalPlayerEntity = playerRepository.findByAvatarId(dto.avatarId());
        if (optionalPlayerEntity.isEmpty()) {
            playerEntity = playerRepository.save(new PlayerEntity(
                    Player.builder()
                            .avatarId(dto.avatarId())
                            .build()
            ));
        } else {
            playerEntity = optionalPlayerEntity.get().toPlayer();
        }

        List<QuestState> questStates = quests.stream().map(i -> QuestState.builder()
                        .quest(i)
                        .player(playerEntity)
                        .questProgress(i.getStartQuestProgress())
                        .build())
                .toList();
        questStateRepository.saveAll(questStates);
    }


    @Transactional
    public void updateQuestProgress(UpdateQuestStateRequestDto dto) {
        QuestState questState = questStateRepository.findById(dto.questStateId());

        QuestProgress questProgress = QuestProgress.from(dto.questProgress());
        questState.updateQuestProgress(questProgress);
        QuestState save = questStateRepository.save(questState);

        questStateRepository.updateChildQuestOpen(save);

    }

    public List<QuestInfoResponseDto> findInProgressQuestByRoadMap(MemberPrincipal memberPrincipal, GetProgressQuestRequestDto dto) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }
        return questStateRepository.findInProgressQuestByRoadMap(dto.getRoadmapId(), memberPrincipal.getPlayerId());
    }

    public List<QuestInfoResponseDto> findConfirmQuestByRoadMap(MemberPrincipal memberPrincipal, GetConfirmQuestRequestDto dto) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }
        return questStateRepository.findConfirmQuestByRoadMap(dto.getRoadmapId(), memberPrincipal.getPlayerId());
    }

    @Transactional
    public void certificateQuest(MemberPrincipal memberPrincipal, Long questId, MultipartFile file) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }

        QuestState questState = questStateRepository.findByPlayerAndQuest(memberPrincipal.getPlayerId(), questId);


        // post 추가
        QuestPost questPost = QuestPost.builder()
                .player(questState.getPlayer())
                .questPostProgressType(QuestPostProgressType.UPLOAD)
                .questState(questState)
                .build();


        QuestPost savedQuestPost = questPostRepository.save(questPost);
        // 파일 이름만 생성
        String fileName = FileUtils.buildFileName(CATEGORY, memberPrincipal.getPlayerId(), savedQuestPost.getId(), file.getOriginalFilename());

        try {
            MultipartFile renamedFile = FileUtils.renameMultipartFile(file, fileName);

            // Synology API로 원본 파일을 직접 업로드 (변환 없이)
            String uploadResult = synologyInfoClient.uploadFile(renamedFile);
            System.out.println(uploadResult);

            QuestPostAttachment questPostAttachment = QuestPostAttachment.builder()
                    .questPost(savedQuestPost)
                    .fileUrl(FileUrl.from(synologyApiUrl + "/upload/" + fileName))
                    .fileName(FileName.from(fileName))
                    .fileSize(FileSize.from(file.getSize()))
                    .build();

            questPostAttachmentRepository.save(questPostAttachment);

        } catch (Exception e) {
            throw new IllegalArgumentException("파일 전송 중 오류가 발생했습니다: " + e.getMessage());
        }


        QuestProgress questProgress = QuestProgress.UPLOAD;
        questState.updateQuestProgress(questProgress);
        questStateRepository.save(questState);
    }

}
