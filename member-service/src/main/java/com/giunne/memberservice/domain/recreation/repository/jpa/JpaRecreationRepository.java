package com.giunne.memberservice.domain.recreation.repository.jpa;

import com.giunne.memberservice.domain.recreation.repository.entity.RecreationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaRecreationRepository extends JpaRepository<RecreationEntity, Long> {
    List<RecreationEntity> findByRecreationCode_RecreationCode(String recreationCode);
}
