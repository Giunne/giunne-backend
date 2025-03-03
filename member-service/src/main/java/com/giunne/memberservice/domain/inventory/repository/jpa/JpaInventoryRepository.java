package com.giunne.memberservice.domain.inventory.repository.jpa;

import com.giunne.memberservice.domain.inventory.repository.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaInventoryRepository extends JpaRepository<InventoryEntity, Long> {
    List<InventoryEntity> findByAvatar_Id(Long avatarId);
}
