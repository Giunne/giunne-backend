package com.giunne.memberservice.domain.recreation.repository.jpa;

import com.giunne.memberservice.domain.recreation.repository.entity.RecreationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRecreationRepository extends JpaRepository<RecreationEntity, Long> {
}
