package com.giunne.memberservice.domain.push.repository;

import com.giunne.memberservice.domain.push.application.interfaces.FcmMessageRepository;
import com.giunne.memberservice.domain.push.domain.FcmToken;
import com.giunne.memberservice.domain.push.repository.entity.FcmTokenEntity;
import com.giunne.memberservice.domain.push.repository.jpa.JpaFcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FcmMessageRepositoryImpl implements FcmMessageRepository {

    private final JpaFcmTokenRepository jpaFcmTokenRepository;

    @Override
    @Transactional
    public FcmToken saveFcmToken(FcmToken fcmToken) {
        Optional<FcmTokenEntity> token = jpaFcmTokenRepository.findByMemberIdAndToken(fcmToken.getMemberId(), fcmToken.getToken());
        if (token.isPresent()) {
            return token.get().toFcmToken();
        }
        FcmTokenEntity save = jpaFcmTokenRepository.save(new FcmTokenEntity(fcmToken));
        return save.toFcmToken();
    }

    @Override
    public List<FcmToken> findByMemberId(Long memberId) {
        return jpaFcmTokenRepository.findByMemberId(memberId)
                .stream()
                .map(FcmTokenEntity::toFcmToken)
                .toList();
    }

    @Override
    @Transactional
    public int deleteByMemberIdAndToken(Long memberId, String token) {
       return jpaFcmTokenRepository.deleteByMemberIdAndToken(memberId, token);
    }

    @Override
    @Transactional
    public int deleteByMemberId(Long memberId) {
        return jpaFcmTokenRepository.deleteByMemberId(memberId);
    }

}
