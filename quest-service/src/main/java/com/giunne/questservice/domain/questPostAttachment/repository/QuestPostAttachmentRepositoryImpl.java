package com.giunne.questservice.domain.questPostAttachment.repository;

import com.giunne.questservice.domain.questPostAttachment.application.interfaces.QuestPostAttachmentRepository;
import com.giunne.questservice.domain.questPostAttachment.domain.QuestPostAttachment;
import com.giunne.questservice.domain.questPostAttachment.repository.entity.QuestPostAttachmentEntity;
import com.giunne.questservice.domain.questPostAttachment.repository.jpa.JpaQuestPostAttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestPostAttachmentRepositoryImpl implements QuestPostAttachmentRepository {

    private final JpaQuestPostAttachmentRepository jpaQuestPostAttachmentRepository;

    @Transactional
    public QuestPostAttachment save(QuestPostAttachment questPostAttachment) {
        QuestPostAttachmentEntity save = jpaQuestPostAttachmentRepository.save(new QuestPostAttachmentEntity(questPostAttachment));
        return save.toQuestPostAttachment();
    }
}
