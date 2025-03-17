package com.giunne.questservice.domain.questPost.application.interfaces;

import com.giunne.questservice.domain.questPost.domain.QuestPost;

public interface QuestPostRepository {
    QuestPost save(QuestPost questPost);
}
