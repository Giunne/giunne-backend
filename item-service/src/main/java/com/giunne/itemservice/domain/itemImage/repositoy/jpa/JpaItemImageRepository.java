package com.giunne.itemservice.domain.itemImage.repositoy.jpa;

import com.giunne.itemservice.domain.item.repository.entity.ItemEntity;
import com.giunne.itemservice.domain.itemImage.repositoy.entity.ItemImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaItemImageRepository extends JpaRepository<ItemImageEntity,Long> {

    @Query("SELECT i FROM ItemImageEntity i WHERE i.id in (:itemImageIdList)")
    List<ItemImageEntity> findByItemImageIdList(@Param("itemImageIdList") List<Long> itemImageIdList);

    @Modifying
    @Query("UPDATE ItemImageEntity i "
            + "SET i.fileUrl.value = :#{#itemImage.fileUrl} "
            + "WHERE i.id = :#{#itemImage.id}")
    void updateItemImage(ItemImageEntity itemImage);

    @Query("SELECT i FROM ItemImageEntity i WHERE i.item.id = (:itemId)")
    List<ItemImageEntity> findByItem_Id(@Param("itemId") Long itemId);
}
