package com.giunne.questservice.domain.questState.repository;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.util.FileUtils;
import com.giunne.questservice.QuestTestConfiguration;
import com.giunne.questservice.domain.questState.application.QuestStateService;
import com.giunne.questservice.domain.questState.application.dto.request.CertificateQuestRequestDto;
import com.giunne.questservice.domain.questState.application.dto.request.UpdateQuestStateRequestDto;
import com.giunne.questservice.domain.questState.application.dto.response.QuestInfoResponseDto;
import com.giunne.questservice.domain.questState.application.interfaces.QuestStateRepository;
import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = QuestTestConfiguration.class)
@Transactional
class QuestStateRepositoryImplTest {

    @Autowired
    private QuestStateRepository questStateRepository;
    @Autowired
    private QuestStateService questStateService;

    @Test
    public void updateChildQuestOpen(){
        UpdateQuestStateRequestDto updateQuestStateRequestDto = UpdateQuestStateRequestDto.builder()
                .questStateId(3046L)
                .questProgress(QuestProgress.CONFIRM.name())
                .build();

        questStateService.updateQuestProgress(updateQuestStateRequestDto);
    }


    @Test
    public void findInProgressQuestByRoadMap(){
        List<QuestInfoResponseDto> inProgressQuestByRoadMap = questStateRepository.findInProgressQuestByRoadMap(1L, 28L);
        System.out.println(inProgressQuestByRoadMap);
    }

//    @Test
//    public void certificateQuest(){
//
//        MemberPrincipal memberPrincipa = new MemberPrincipal(18L, 23L, MemberRole.ROLE_ADMIN.name());
//        CertificateQuestRequestDto certificateQuestRequestDto = CertificateQuestRequestDto.builder()
//                .questId(645L)
//                .build();
//        questStateService.certificateQuest(memberPrincipa, certificateQuestRequestDto);
//    }

    @Test
    public void fileUpload(){
        String category = FileUtils.buildFileName("CATEGORY", 1L,10L, "abc.mp4");
        System.out.println(category);
    }
}