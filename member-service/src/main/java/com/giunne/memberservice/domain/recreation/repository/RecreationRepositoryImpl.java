package com.giunne.memberservice.domain.recreation.repository;

import com.giunne.memberservice.domain.recreation.application.interfaces.RecreationRepository;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import com.giunne.memberservice.domain.recreation.repository.entity.RecreationEntity;
import com.giunne.memberservice.domain.recreation.repository.jpa.JpaRecreationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RecreationRepositoryImpl implements RecreationRepository {

    private final JpaRecreationRepository jpaRecreationRepository;

    @Override
    public Recreation createRecreation(Recreation recreation) {
        RecreationEntity recreationEntity = new RecreationEntity(recreation);
        RecreationEntity entity = jpaRecreationRepository.save(recreationEntity);
        return entity.toRecreation();
    }
}
