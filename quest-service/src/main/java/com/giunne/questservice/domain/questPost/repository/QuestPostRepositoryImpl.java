package com.giunne.questservice.domain.questPost.repository;

import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostRepository;
import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import com.giunne.questservice.domain.questPost.repository.jpa.JpaQuestPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestPostRepositoryImpl implements QuestPostRepository {

    private final JpaQuestPostRepository jpaQuestPostRepository;

    @Transactional
    public QuestPost save(QuestPost questPost) {
        QuestPostEntity save = jpaQuestPostRepository.save(new QuestPostEntity(questPost));
        return save.toQuestPost();
    }

}
