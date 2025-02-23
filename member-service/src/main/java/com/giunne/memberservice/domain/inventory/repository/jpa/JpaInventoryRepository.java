package com.giunne.memberservice.domain.inventory.repository.jpa;

import com.giunne.memberservice.domain.inventory.repository.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInventoryRepository extends JpaRepository<InventoryEntity, Long> {
}
