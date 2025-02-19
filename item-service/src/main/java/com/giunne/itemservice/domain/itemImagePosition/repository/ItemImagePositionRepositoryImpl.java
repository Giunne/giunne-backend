package com.giunne.itemservice.domain.itemImagePosition.repository;

import com.giunne.itemservice.domain.itemImagePosition.application.interfaces.ItemImagePositionRepository;
import com.giunne.itemservice.domain.itemImagePosition.domain.ItemImagePosition;
import com.giunne.itemservice.domain.itemImagePosition.repository.entity.ItemImagePositionEntity;
import com.giunne.itemservice.domain.itemImagePosition.repository.jpa.JpaItemImagePositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ItemImagePositionRepositoryImpl implements ItemImagePositionRepository {

    private final JpaItemImagePositionRepository jpaItemImagePositionRepository;

    @Override
    public List<ItemImagePosition> saveAll(List<ItemImagePosition> itemImagePositions) {
        List<ItemImagePositionEntity> itemImagePositionEntities = itemImagePositions.stream().map(ItemImagePositionEntity::new).toList();
        List<ItemImagePositionEntity> saveAll = jpaItemImagePositionRepository.saveAll(itemImagePositionEntities);
        return saveAll.stream().map(ItemImagePositionEntity::toItemImagePosition).toList();
    }
}
