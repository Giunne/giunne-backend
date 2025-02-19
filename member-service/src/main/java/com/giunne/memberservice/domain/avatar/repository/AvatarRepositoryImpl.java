package com.giunne.memberservice.domain.avatar.repository;

import com.giunne.memberservice.domain.avatar.application.interfaces.AvatarRepository;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.avatar.repository.entity.AvatarEntity;
import com.giunne.memberservice.domain.avatar.repository.entity.QAvatarEntity;
import com.giunne.memberservice.domain.avatar.repository.jpa.JpaAvatarRepository;
import com.giunne.memberservice.domain.member.repository.entity.QMemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AvatarRepositoryImpl implements AvatarRepository {

    private final JpaAvatarRepository jpaAvatarRepository;
    private final JPAQueryFactory queryFactory;
    private static final QMemberEntity memberEntity = QMemberEntity.memberEntity;
    private static final QAvatarEntity avatarEntity = QAvatarEntity.avatarEntity;


    @Override
    public Avatar createAvatar(Avatar avatar){
        AvatarEntity avatarEntity = new AvatarEntity(avatar);
        AvatarEntity entity = jpaAvatarRepository.save(avatarEntity);
        return entity.toAvatar();
    }

}
