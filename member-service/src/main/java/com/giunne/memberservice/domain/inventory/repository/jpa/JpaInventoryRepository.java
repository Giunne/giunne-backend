package com.giunne.memberservice.domain.inventory.repository.jpa;

import com.giunne.memberservice.domain.inventory.repository.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpaInventoryRepository extends JpaRepository<InventoryEntity, Long> {
    List<InventoryEntity> findByAvatar_Id(Long avatarId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE InventoryEntity i "
            + "SET i.isWear = true "
            + "WHERE i.avatar.id = :#{#playerId} "
            + "AND i.itemInfo.itemNo in(:#{#itemIdList}) "
    )
    int wearItems(@Param("playerId") Long playerId, @Param("itemIdList")List<Long> itemIdList);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE InventoryEntity i "
            + "SET i.isWear = false "
            + "WHERE i.avatar.id = :#{#playerId} "
            + "AND i.itemInfo.categoryNo != 1 "
    )
    int takeOffItems(@Param("playerId")Long playerId);

}
