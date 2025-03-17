package com.giunne.questservice.domain.questPostAttachment.repository.jpa;

import com.giunne.questservice.domain.questPostAttachment.repository.entity.QuestPostAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuestPostAttachmentRepository extends JpaRepository<QuestPostAttachmentEntity, Long> {
}
