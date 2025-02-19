package com.giunne.itemservice.domain.store.repository.jpa;

import com.giunne.itemservice.domain.store.repository.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStoreRepository extends JpaRepository<StoreEntity, Long> {
}
