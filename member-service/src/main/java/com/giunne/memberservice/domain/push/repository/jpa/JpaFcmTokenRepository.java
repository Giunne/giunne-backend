package com.giunne.memberservice.domain.push.repository.jpa;

import com.giunne.memberservice.domain.push.repository.entity.FcmTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaFcmTokenRepository extends JpaRepository<FcmTokenEntity, Long> {
    List<FcmTokenEntity> findByMemberId(Long memberId);
    Optional<FcmTokenEntity> findByMemberIdAndToken(Long memberId, String token);
    int deleteByMemberIdAndToken(Long memberId, String token);
    int deleteByMemberId(Long memberId);
}
