package com.giunne.itemservice.domain.itemImage.repositoy;

import com.giunne.itemservice.domain.item.repository.entity.ItemEntity;
import com.giunne.itemservice.domain.item.repository.entity.QItemEntity;
import com.giunne.itemservice.domain.itemImage.application.interfaces.ItemImageRepository;
import com.giunne.itemservice.domain.itemImage.domain.ItemImage;
import com.giunne.itemservice.domain.itemImage.repositoy.entity.ItemImageEntity;
import com.giunne.itemservice.domain.itemImage.repositoy.entity.QItemImageEntity;
import com.giunne.itemservice.domain.itemImage.repositoy.jpa.JpaItemImageRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ItemImageRepositoryImpl implements ItemImageRepository {

    private final JpaItemImageRepository jpaItemImageRepository;
    private final JPAQueryFactory queryFactory;
    private final QItemEntity itemEntity = QItemEntity.itemEntity;
    private final QItemImageEntity itemImageEntity = QItemImageEntity.itemImageEntity;

    @Override
    public List<ItemImage> saveAll(List<ItemImage> itemImageList) {
        List<ItemImageEntity> itemImageEntities = itemImageList.stream().map(ItemImageEntity::new).toList();
        List<ItemImageEntity> saveAll = jpaItemImageRepository.saveAll(itemImageEntities);
        return saveAll.stream().map(ItemImageEntity::toItemImage).toList();
    }

    @Override
    public void updateItemImage(List<ItemImage> itemImageList) {
        List<ItemImageEntity> itemImageEntities = itemImageList.stream().map(ItemImageEntity::new).toList();
        itemImageEntities.forEach(jpaItemImageRepository::updateItemImage);
    }

    @Override
    public List<ItemImage> findByItemImageIdList(List<Long> itemImageList) {
        List<ItemImageEntity> itemImageEntities = jpaItemImageRepository.findByItemImageIdList(itemImageList);
        return itemImageEntities.stream().map(ItemImageEntity::toItemImage).toList();
    }

    @Override
    public List<ItemImage> findByItemId(Long itemId) {
        List<ItemImageEntity> imageEntities = jpaItemImageRepository.findByItem_Id(itemId);
        return imageEntities.stream().map(ItemImageEntity::toItemImage).toList();
    }

}
