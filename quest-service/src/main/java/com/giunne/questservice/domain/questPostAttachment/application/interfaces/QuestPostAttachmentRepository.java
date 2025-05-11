package com.giunne.questservice.domain.questPostAttachment.application.interfaces;

import com.giunne.questservice.domain.questPostAttachment.domain.QuestPostAttachment;

public interface QuestPostAttachmentRepository {
    QuestPostAttachment save(QuestPostAttachment questPostAttachment);

    void deleteByQuestPostId(Long questPostId);
}
