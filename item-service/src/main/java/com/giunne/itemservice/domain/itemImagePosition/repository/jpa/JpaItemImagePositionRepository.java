package com.giunne.itemservice.domain.itemImagePosition.repository.jpa;

import com.giunne.itemservice.domain.itemImagePosition.repository.entity.ItemImagePositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItemImagePositionRepository extends JpaRepository<ItemImagePositionEntity, Long> {
}
