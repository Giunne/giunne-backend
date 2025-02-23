package com.giunne.memberservice.domain.avatar.repository.jpa;

import com.giunne.memberservice.domain.avatar.repository.entity.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaAvatarRepository extends JpaRepository<AvatarEntity, Long> {

    List<AvatarEntity> findByMemberId(Long memberId);
}
