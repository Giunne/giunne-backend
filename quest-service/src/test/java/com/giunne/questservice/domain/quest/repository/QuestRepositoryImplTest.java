package com.giunne.questservice.domain.quest.repository;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.questservice.QuestTestConfiguration;
import com.giunne.questservice.domain.quest.application.dto.request.GetUploadQuestForStudentRequestDto;
import com.giunne.questservice.domain.quest.application.dto.response.GetUploadQuestResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostResponseDto;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = QuestTestConfiguration.class)
@Transactional
class QuestRepositoryImplTest {

    @Autowired
    private QuestPostRepository questPostRepository;

    @Test
    @Rollback(false)
    @DisplayName("게시물 조회 ")
    void findById(){
        // when
        GetPostResponseDto byId = questPostRepository.findById(24L);
        System.out.println(byId);
    }

    @Test
    @Rollback(false)
    @DisplayName("퀘스트 회원 조회 ")
    void findMyQuest(){
        // when
        questPostRepository.findMyQuest(24L, 28L);
    }

    @Test
    @Rollback(false)
    @DisplayName("게시판 리스트 조회 ")
    void findUploadQuest(){
        // when
        GetUploadQuestForStudentRequestDto getUploadQuestForStudentRequestDto = new GetUploadQuestForStudentRequestDto(null,"테스트 학생 2" );
        PaginationModel<GetUploadQuestResponseDto> uploadQuest = questPostRepository.findUploadQuest(getUploadQuestForStudentRequestDto);
        System.out.println(uploadQuest);
    }

}