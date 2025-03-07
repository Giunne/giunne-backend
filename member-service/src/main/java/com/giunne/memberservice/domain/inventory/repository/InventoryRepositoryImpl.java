package com.giunne.memberservice.domain.inventory.repository;

import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.inventory.application.interfaces.InventoryRepository;
import com.giunne.memberservice.domain.inventory.domain.Inventory;
import com.giunne.memberservice.domain.inventory.repository.entity.InventoryEntity;
import com.giunne.memberservice.domain.inventory.repository.jpa.JpaInventoryRepository;
import com.giunne.memberservice.domain.member.application.interfaces.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final JpaInventoryRepository jpaInventoryRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = false)
    public Inventory insertInventory(Inventory inventory) {
        InventoryEntity inventoryEntity = new InventoryEntity(inventory);
        InventoryEntity saved = jpaInventoryRepository.save(inventoryEntity);
        return saved.toInventory();
    }

    @Override
    public List<Inventory> findInventoryByAvatar(Avatar avatar) {
        List<InventoryEntity> inventoryEntities = jpaInventoryRepository.findByAvatar_Id(avatar.getId());
        return inventoryEntities.stream().map(InventoryEntity::toInventory).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public void takeOffItems(Avatar avatar) {
        int takeOffItemsCnt= jpaInventoryRepository.takeOffItems(avatar.getId());

    }

    @Override
    @Transactional(readOnly = false)
    public void wearingItems(Avatar avatar, List<Long> itemidList) {
        if(itemidList.isEmpty())
            return;

        int wearItemsCnt = jpaInventoryRepository.wearItems(avatar.getId(), itemidList);

    }

}
