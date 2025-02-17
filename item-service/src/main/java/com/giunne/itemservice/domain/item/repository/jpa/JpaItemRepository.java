package com.giunne.itemservice.domain.item.repository.jpa;

import com.giunne.itemservice.domain.item.repository.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query("SELECT i FROM ItemEntity i WHERE i.id in (:itemIdList)")
    List<ItemEntity> findByItemIdList(@Param("itemIdList") List<Long> itemIdList);

    @Modifying
    @Query("UPDATE ItemEntity i "
            + "SET i.itemName.itemName = :#{#item.itemName.itemName}, "
            + "i.itemDescription.description = :#{#item.itemDescription.description}, "
            + "i.price.value = :#{#item.price.value}, "
            + "i.needLevel.value = :#{#item.needLevel.value}, "
            + "i.sortSeq.value = :#{#item.sortSeq.value}, "
            + "i.stock.value = :#{#item.stock.value}, "
            + "i.category.id = :#{#item.category.id}, "
            + "i.store.id = :#{#item.store.id}, "
            + "i.itemGrade = :#{#item.itemGrade} "
            + "WHERE i.id = :#{#item.id}")
    void updateItem(ItemEntity item);
}
