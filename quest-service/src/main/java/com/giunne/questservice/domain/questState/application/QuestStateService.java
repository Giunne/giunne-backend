package com.giunne.questservice.domain.questState.application;


import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.infra.external.domain.member.client.MemberInfoClient;
import com.giunne.commonservice.infra.external.domain.synology.client.SynologyInfoClient;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.commonservice.util.FileUtils;
import com.giunne.questservice.domain.quest.application.dto.request.CertificateRequestDto;
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
import org.springframework.http.HttpStatus;
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
    private final MemberInfoClient memberInfoClient;

    private final String CATEGORY = "certificate";
    private static final String FILE_UPLOAD_FAIL = "파일 업로드 실패";


    @Transactional
    public void savePlayerQuestStates(CreateQuestStateRequestDto dto) {
        List<Quest> quests = questRepository.findByRoadMap(dto.roadMapId());

        Player playerEntity;
        Optional<PlayerEntity> optionalPlayerEntity = playerRepository.findByAvatarId(dto.avatarId());

        if (optionalPlayerEntity.isPresent()) {
            playerEntity = optionalPlayerEntity.get().toPlayer();
        }else {
            playerEntity = playerRepository.save(new PlayerEntity(
                    Player.builder()
                            .avatarId(dto.avatarId())
                            .avatarNickname(dto.avatarNickname())
                            .memberId(dto.memberId())
                            .userName(dto.userName())
                            .nickname(dto.nickname())
                            .build()
            ));
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
            if (FILE_UPLOAD_FAIL.equals(uploadResult)) {
                throw new IllegalArgumentException("파일 업로드에 실패했습니다");
            }

            QuestPostAttachment questPostAttachment = QuestPostAttachment.builder()
                    .questPost(savedQuestPost)
                    .fileUrl(FileUrl.from(synologyApiUrl + "/upload/" + fileName))
                    .fileName(FileName.from(fileName))
                    .fileSize(FileSize.from(file.getSize()))
                    .build();

            questPostAttachmentRepository.save(questPostAttachment);

        } catch (Exception e) {
            throw new IllegalArgumentException("파일 전송 중 오류가 발생했습니다.");
        }


        QuestProgress questProgress = QuestProgress.UPLOAD;
        questState.updateQuestProgress(questProgress);
        questStateRepository.save(questState);
    }

    @Transactional
    public void certificate(MemberPrincipal memberPrincipal, CertificateRequestDto dto) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }

        if (memberPrincipal.getRole() != MemberRole.ROLE_TEACHER) {
            throw new IllegalArgumentException("선생님만 채점이 가능합니다.");
        }

        QuestPost questPost = questPostRepository.getQuestPost(dto.questPostId());
        questPost.passOrFailProgress(dto.isPass());
        questPostRepository.updatePostProgress(questPost);
        QuestState foundQuestState = questStateRepository.findByQuestPostId(dto.questPostId());

        if(!QuestProgress.UPLOAD.equals(foundQuestState.getQuestProgress())){
            throw new IllegalArgumentException("업로드 상태만 채점이 가능합니다.");
        }

        if (questPost.getQuestPostProgressType() == QuestPostProgressType.UPLOAD ||
                questPost.getQuestPostProgressType() == QuestPostProgressType.FAIL) {
            updateQuestProgress(UpdateQuestStateRequestDto.builder()
                    .questStateId(foundQuestState.getId())
                    .questProgress(QuestProgress.CHECK.name())
                    .build());

            return;
        }

        if (questPost.getQuestPostProgressType() == QuestPostProgressType.PASS) {
            Quest quest = questRepository.findById(foundQuestState.getQuest().getId());
            foundQuestState.getCurrentApproveCount().increase();
            foundQuestState.getHasExtraPoints().updateExtraPoints(dto.hasExtraPoints());

            if (quest.getNeedApproveCount().getValue() >= (foundQuestState.getCurrentApproveCount().getValue())) {
                foundQuestState.getStarPoint().updateStartPoint(dto.starPoint());
                questStateRepository.save(foundQuestState);
                updateQuestProgress(UpdateQuestStateRequestDto.builder()
                        .questStateId(foundQuestState.getId())
                        .questProgress(QuestProgress.CONFIRM.name())
                        .build());

                // 경험치 증가
                Response<String> memberPointExperiencResponse = memberInfoClient.increaseExperience(questPost.getPlayer().getAvatarId(), quest.getRewardExp().getValue());
                if (memberPointExperiencResponse.code() != HttpStatus.OK.value()) {
                    throw new IllegalArgumentException("경험치 증가 실패");
                }

                // 포인트 증가
                Response<String> memberPointIncreaseResponse = memberInfoClient.increasePoint(questPost.getPlayer().getAvatarId(), quest.getRewardPoint().calculatedPoint(dto.starPoint()));
                if (memberPointIncreaseResponse.code() != HttpStatus.OK.value()) {
                    throw new IllegalArgumentException("포인트 증가 실패");
                }
            }

            return;
        }
    }

}
