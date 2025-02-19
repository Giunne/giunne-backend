package com.giunne.memberservice.domain.avatar.repository.jpa;

import com.giunne.memberservice.domain.avatar.repository.entity.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAvatarRepository extends JpaRepository<AvatarEntity, Long> {
}
