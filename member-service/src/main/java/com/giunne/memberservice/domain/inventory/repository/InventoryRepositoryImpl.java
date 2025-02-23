package com.giunne.memberservice.domain.inventory.repository;

import com.giunne.memberservice.domain.inventory.application.interfaces.InventoryRepository;
import com.giunne.memberservice.domain.inventory.domain.Inventory;
import com.giunne.memberservice.domain.inventory.repository.entity.InventoryEntity;
import com.giunne.memberservice.domain.inventory.repository.jpa.JpaInventoryRepository;
import com.giunne.memberservice.domain.member.application.interfaces.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final JpaInventoryRepository jpaInventoryRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Inventory insertInventory(Inventory inventory) {
        InventoryEntity inventoryEntity = new InventoryEntity(inventory);
        InventoryEntity saved = jpaInventoryRepository.save(inventoryEntity);
        return saved.toInventory();
    }



}
