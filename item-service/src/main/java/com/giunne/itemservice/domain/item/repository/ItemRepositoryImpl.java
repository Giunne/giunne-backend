package com.giunne.itemservice.domain.item.repository;

import com.giunne.commonservice.domain.item.ItemGrade;
import com.giunne.commonservice.infra.external.domain.item.client.dto.request.GetItemsRequestDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.request.GetWearingItemsRequestDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetItemResponseDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetWearingItemResponseDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.itemservice.domain.category.repository.entity.QCategoryEntity;
import com.giunne.itemservice.domain.item.application.dto.request.GetItemPageRequestDto;
import com.giunne.itemservice.domain.item.application.dto.response.GetItemPageResponseDto;
import com.giunne.itemservice.domain.item.application.interfaces.ItemRepository;
import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.item.domain.type.GachaType;
import com.giunne.itemservice.domain.item.repository.entity.ItemEntity;
import com.giunne.itemservice.domain.item.repository.entity.QItemEntity;
import com.giunne.itemservice.domain.item.repository.jpa.JpaItemRepository;
import com.giunne.itemservice.domain.itemImage.repositoy.entity.QItemImageEntity;
import com.giunne.itemservice.domain.itemImagePosition.repository.entity.QItemImagePositionEntity;
import com.giunne.itemservice.domain.orders.api.response.GetItemOrderGachaResponseDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.giunne.commonservice.util.PaginationUtil.getPageRequest;
import static com.giunne.commonservice.util.PaginationUtil.toPaginationModel;

@Repository
@AllArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final JpaItemRepository jpaItemRepository;
    private final JPAQueryFactory queryFactory;
    private static final QItemEntity itemEntity = QItemEntity.itemEntity;
    private static final QCategoryEntity categoryEntity = QCategoryEntity.categoryEntity;
    private static final QItemImageEntity itemImageEntity = QItemImageEntity.itemImageEntity;
    private static final QItemImagePositionEntity itemImagePositionEntity = QItemImagePositionEntity.itemImagePositionEntity;
    private static final Long CHARACTER_ID = 1L;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Item> saveAll(List<Item> itemList) {
        List<ItemEntity> itemEntities = itemList.stream().map(ItemEntity::new).toList();
        List<ItemEntity> saveAll = jpaItemRepository.saveAll(itemEntities);
        return saveAll.stream().map(ItemEntity::toItem).toList();
    }

    @Override
    public void updateItem(List<Item> itemList) {
        List<ItemEntity> itemEntities = itemList.stream().map(ItemEntity::new).toList();
        itemEntities.forEach(jpaItemRepository::updateItem);
    }

    @Override
    public List<Item> findByItemIdList(List<Long> itemIdList) {
        List<ItemEntity> itemEntities = jpaItemRepository.findByItemIdList(itemIdList);
        return itemEntities.stream().map(ItemEntity::toItem).toList();
    }

    @Override
    public Item findById(Long id) {
        ItemEntity itemEntity = jpaItemRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return itemEntity.toItem();
    }

    @Override
    public PaginationModel<GetItemPageResponseDto> findByCategory(GetItemPageRequestDto dto) {
        Pageable pageable = getPageRequest(
                dto.getPageIndex(),
                dto.getPageSize(),
                Sort.by(Sort.Direction.valueOf(dto.getDirection()), dto.getSortProperty())
        );

        // 1. 아이템 개수 조회 (총 개수)
        JPAQuery<Long> countQuery = queryFactory
                .select(itemEntity.count())
                .from(itemEntity)
                .where(
                        itemEntity.category.id.eq(dto.getCategoryId())
                );

        // 2. 아이템 + 이미지 + 위치 정보 조회
        List<Tuple> results = queryFactory
                .select(
                        itemEntity.id,
                        itemEntity.itemName.itemName,
                        itemEntity.itemDescription.description,
                        itemEntity.price.value,
                        itemEntity.needLevel.value,
                        itemEntity.sortSeq.value,
                        itemEntity.category.id,
                        itemEntity.category.categoryName.categoryName,
                        itemEntity.itemGrade,
                        itemEntity.thumbnailUrl.value

                )
                .from(itemEntity)
                .where(itemEntity.category.id.eq(dto.getCategoryId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> idList = results.stream().map(i -> i.get(itemEntity.id)).toList();

        List<Tuple> joinResults = queryFactory
                .select(
                        itemEntity.id,
                        itemEntity.itemName.itemName,
                        itemEntity.itemDescription.description,
                        itemEntity.price.value,
                        itemEntity.needLevel.value,
                        itemEntity.sortSeq.value,
                        itemEntity.category.id,
                        itemEntity.category.categoryName.categoryName,
                        itemEntity.itemGrade,
                        itemEntity.thumbnailUrl.value,

                        itemImageEntity.id,
                        itemImageEntity.fileUrl.value,
                        itemImageEntity.isRepresent.value,
                        itemImageEntity.level.value,

                        itemImagePositionEntity.id,
                        itemImagePositionEntity.position.positionX,
                        itemImagePositionEntity.position.positionY,
                        itemImagePositionEntity.position.positionZ,
                        itemImagePositionEntity.level
                )
                .from(itemEntity)
                .leftJoin(itemImageEntity).on(itemEntity.id.eq(itemImageEntity.item.id))
                .leftJoin(itemImagePositionEntity).on(itemImageEntity.id.eq(itemImagePositionEntity.itemImage.id))
                .where(itemEntity.id.in(idList))
                .fetch();

        // 3. 결과를 Map<Long, GetItemPageResponseDto> 형태로 변환
        Map<Long, GetItemPageResponseDto> itemMap = new LinkedHashMap<>();


        for (Tuple tuple : joinResults) {
            Long itemId = tuple.get(itemEntity.id);
            GetItemPageResponseDto itemDto = itemMap.get(itemId);

            // 아이템이 처음 추가될 때만 생성
            if (itemDto == null) {
                itemDto = new GetItemPageResponseDto();
                itemDto.setId(itemId);
                itemDto.setItemName(tuple.get(itemEntity.itemName.itemName));
                itemDto.setItemDescription(tuple.get(itemEntity.itemDescription.description));
                itemDto.setPrice(tuple.get(itemEntity.price.value));
                itemDto.setNeedLevel(tuple.get(itemEntity.needLevel.value));
                itemDto.setSortSeq(tuple.get(itemEntity.sortSeq.value));
                itemDto.setCategoryId(tuple.get(itemEntity.category.id));
                itemDto.setItemGrade(tuple.get(itemEntity.itemGrade));
                itemDto.setThumbnailUrl(tuple.get(itemEntity.thumbnailUrl.value));
                itemDto.setItemImages(new ArrayList<>()); // 이미지 리스트 초기화
                itemMap.put(itemId, itemDto);
            }

            // 4. 이미지 정보 추가
            if (tuple.get(itemImageEntity.id) != null) {
                Long imageId = tuple.get(itemImageEntity.id);
                GetItemPageResponseDto.ItemImage image = itemDto.getItemImages().stream()
                        .filter(img -> img.getId().equals(imageId))
                        .findFirst()
                        .orElse(null);

                if (image == null) {
                    image = new GetItemPageResponseDto.ItemImage();
                    image.setId(imageId);
                    image.setFileUrl(tuple.get(itemImageEntity.fileUrl.value));
                    image.setIsRepresent(tuple.get(itemImageEntity.isRepresent.value));
                    image.setLevel(tuple.get(itemImageEntity.level.value));
                    image.setItemImagePositions(new ArrayList<>()); // 위치 리스트 초기화
                    itemDto.getItemImages().add(image);
                }

                // 5. 이미지 위치 정보 추가
                if (tuple.get(itemImagePositionEntity.id) != null) {
                    GetItemPageResponseDto.ItemImage.ItemImagePosition position = new GetItemPageResponseDto.ItemImage.ItemImagePosition();
                    position.setId(tuple.get(itemImagePositionEntity.id));
                    position.setPositionX(tuple.get(itemImagePositionEntity.position.positionX));
                    position.setPositionY(tuple.get(itemImagePositionEntity.position.positionY));
                    position.setPositionZ(tuple.get(itemImagePositionEntity.position.positionZ));
                    position.setLevel(tuple.get(itemImagePositionEntity.level));
                    image.getItemImagePositions().add(position);
                }
            }
        }

        // 6. 리스트 변환 후 페이징 적용
        List<GetItemPageResponseDto> itemList = new ArrayList<>(itemMap.values());
        Page<GetItemPageResponseDto> pageResult = PageableExecutionUtils.getPage(itemList, pageable, countQuery::fetchCount);

        return toPaginationModel(pageResult);
    }

    @Override
    public List<GetWearingItemResponseDto> findWearingItems(GetWearingItemsRequestDto dto) {

        List<Tuple> results = queryFactory
                .select(
                        itemEntity.id,
                        itemEntity.itemName.itemName,
                        itemEntity.itemDescription.description,
                        itemEntity.price.value,
                        itemEntity.needLevel.value,
                        itemEntity.sortSeq.value,
                        itemEntity.category.id,
                        itemEntity.category.categoryName.categoryName,
                        itemEntity.itemGrade
                )
                .from(itemEntity)
                .where(itemEntity.id.in(dto.getItemIds()),
                        itemEntity.needLevel.value.loe(dto.getLevel())

                )
                .fetch();

        List<Long> idList = results.stream().map(i -> i.get(itemEntity.id)).toList();

        List<Tuple> joinResults = queryFactory
                .select(
                        itemEntity.id,
                        itemEntity.itemName.itemName,
                        itemEntity.itemDescription.description,
                        itemEntity.price.value,
                        itemEntity.needLevel.value,
                        itemEntity.sortSeq.value,
                        itemEntity.category.id,
                        itemEntity.category.categoryName.categoryName,
                        itemEntity.itemGrade,

                        itemImageEntity.id,
                        itemImageEntity.fileUrl.value,
                        itemImageEntity.isRepresent.value,
                        itemImageEntity.level.value,

                        itemImagePositionEntity.id,
                        itemImagePositionEntity.position.positionX,
                        itemImagePositionEntity.position.positionY,
                        itemImagePositionEntity.position.positionZ,
                        itemImagePositionEntity.level
                )
                .from(itemEntity)
                .leftJoin(itemImageEntity).on(itemEntity.id.eq(itemImageEntity.item.id))
                .leftJoin(itemImagePositionEntity).on(itemImageEntity.id.eq(itemImagePositionEntity.itemImage.id))
                .where(itemEntity.id.in(idList),
                        itemImageEntity.level.value.in(0, dto.getLevel())
                )
                .fetch();

        // 3. 결과를 Map<Long, GetWearingItemResponseDto> 형태로 변환
        Map<Long, GetWearingItemResponseDto> itemMap = new LinkedHashMap<>();

        for (Tuple tuple : joinResults) {
            Long itemId = tuple.get(itemEntity.id);
            GetWearingItemResponseDto itemDto = itemMap.get(itemId);

            // 아이템이 처음 추가될 때만 생성
            if (itemDto == null) {
                itemDto = new GetWearingItemResponseDto();
                itemDto.setId(itemId);
                itemDto.setItemName(tuple.get(itemEntity.itemName.itemName));
                itemDto.setItemDescription(tuple.get(itemEntity.itemDescription.description));
                itemDto.setPrice(tuple.get(itemEntity.price.value));
                itemDto.setNeedLevel(tuple.get(itemEntity.needLevel.value));
                itemDto.setSortSeq(tuple.get(itemEntity.sortSeq.value));
                itemDto.setCategoryId(tuple.get(itemEntity.category.id));
                itemDto.setItemGrade(tuple.get(itemEntity.itemGrade));
//                itemDto.setItemImage(new GetWearingItemResponseDto.ItemImage()); // 이미지 리스트 초기화
                itemMap.put(itemId, itemDto);
            }

            // 4. 이미지 정보 추가
            if (tuple.get(itemImageEntity.id) != null) {
                Long imageId = tuple.get(itemImageEntity.id);
//                GetWearingItemResponseDto.ItemImage image = null;
                GetWearingItemResponseDto.ItemImage image = itemDto.getItemImage();

//                GetWearingItemResponseDto.ItemImage image = itemDto.getItemImage().stream()
//                        .filter(img -> img.getId().equals(imageId))
//                        .findFirst()
//                        .orElse(null);

                if (image == null) {
                    image = new GetWearingItemResponseDto.ItemImage();
                    image.setId(imageId);
                    image.setFileUrl(tuple.get(itemImageEntity.fileUrl.value));
                    image.setIsRepresent(tuple.get(itemImageEntity.isRepresent.value));
                    image.setLevel(tuple.get(itemImageEntity.level.value));
//                    image.setItemImagePositions(new ArrayList<>()); // 위치 리스트 초기화
                    itemDto.setItemImage(image);
//                    itemDto.getItemImage().add(image);
                }

                // 5. 이미지 위치 정보 추가
                if (tuple.get(itemImagePositionEntity.id) != null && Objects.equals(tuple.get(itemImagePositionEntity.level), dto.getLevel())) {
                    GetWearingItemResponseDto.ItemImage.ItemImagePosition position = new GetWearingItemResponseDto.ItemImage.ItemImagePosition();
                    position.setId(tuple.get(itemImagePositionEntity.id));
                    position.setPositionX(tuple.get(itemImagePositionEntity.position.positionX));
                    position.setPositionY(tuple.get(itemImagePositionEntity.position.positionY));
                    position.setPositionZ(tuple.get(itemImagePositionEntity.position.positionZ));
                    position.setLevel(tuple.get(itemImagePositionEntity.level));
                    image.setItemImagePosition(position);
                }
            }
        }

        // 6. 리스트 변환 후 페이징 적용
        List<GetWearingItemResponseDto> itemList = new ArrayList<>(itemMap.values());
        return itemList;
    }

    @Override
    public PaginationModel<GetItemResponseDto> findByItems(GetItemsRequestDto dto) {
        Pageable pageable = getPageRequest(
                dto.getPageIndex(),
                dto.getPageSize(),
                Sort.by(Sort.Direction.valueOf(dto.getDirection()), dto.getSortProperty())
        );

        // 1. 아이템 개수 조회 (총 개수)
        JPAQuery<Long> countQuery = queryFactory
                .select(itemEntity.count())
                .from(itemEntity)
                .where(
                        itemEntity.category.id.eq(dto.getCategoryId())
                );


        // 2. 아이템 + 이미지 + 위치 정보 조회
        List<Tuple> results = queryFactory
                .select(
                        itemEntity.id,
                        itemEntity.itemName.itemName,
                        itemEntity.itemDescription.description,
                        itemEntity.price.value,
                        itemEntity.needLevel.value,
                        itemEntity.sortSeq.value,
                        itemEntity.category.id,
                        itemEntity.category.categoryName.categoryName,
                        itemEntity.itemGrade,
                        itemEntity.thumbnailUrl.value

                )
                .from(itemEntity)
                .where(itemEntity.id.in(dto.getItemIds())
                        .and(itemEntity.category.id.eq(dto.getCategoryId()))
                )
                .fetch();

        List<Long> idList = results.stream().map(i -> i.get(itemEntity.id)).toList();

        List<Tuple> joinResults = queryFactory
                .select(
                        itemEntity.id,
                        itemEntity.itemName.itemName,
                        itemEntity.itemDescription.description,
                        itemEntity.price.value,
                        itemEntity.needLevel.value,
                        itemEntity.sortSeq.value,
                        itemEntity.category.id,
                        itemEntity.category.categoryName.categoryName,
                        itemEntity.itemGrade,
                        itemEntity.thumbnailUrl.value,

                        itemImageEntity.id,
                        itemImageEntity.fileUrl.value,
                        itemImageEntity.isRepresent.value,
                        itemImageEntity.level.value,

                        itemImagePositionEntity.id,
                        itemImagePositionEntity.position.positionX,
                        itemImagePositionEntity.position.positionY,
                        itemImagePositionEntity.position.positionZ,
                        itemImagePositionEntity.level
                )
                .from(itemEntity)
                .leftJoin(itemImageEntity).on(itemEntity.id.eq(itemImageEntity.item.id))
                .leftJoin(itemImagePositionEntity).on(itemImageEntity.id.eq(itemImagePositionEntity.itemImage.id))
                .where(itemEntity.id.in(idList))
                .fetch();

        // 3. 결과를 Map<Long, GetItemPageResponseDto> 형태로 변환
        Map<Long, GetItemResponseDto> itemMap = new LinkedHashMap<>();


        for (Tuple tuple : joinResults) {
            Long itemId = tuple.get(itemEntity.id);
            GetItemResponseDto itemDto = itemMap.get(itemId);

            // 아이템이 처음 추가될 때만 생성
            if (itemDto == null) {
                itemDto = new GetItemResponseDto();
                itemDto.setId(itemId);
                itemDto.setItemName(tuple.get(itemEntity.itemName.itemName));
                itemDto.setItemDescription(tuple.get(itemEntity.itemDescription.description));
                itemDto.setPrice(tuple.get(itemEntity.price.value));
                itemDto.setNeedLevel(tuple.get(itemEntity.needLevel.value));
                itemDto.setSortSeq(tuple.get(itemEntity.sortSeq.value));
                itemDto.setCategoryId(tuple.get(itemEntity.category.id));
                itemDto.setItemGrade(tuple.get(itemEntity.itemGrade));
                itemDto.setThumbnailUrl(tuple.get(itemEntity.thumbnailUrl.value));
                itemDto.setItemImages(new ArrayList<>()); // 이미지 리스트 초기화
                itemMap.put(itemId, itemDto);
            }

            // 4. 이미지 정보 추가
            if (tuple.get(itemImageEntity.id) != null) {
                Long imageId = tuple.get(itemImageEntity.id);
                GetItemResponseDto.ItemImage image = itemDto.getItemImages().stream()
                        .filter(img -> img.getId().equals(imageId))
                        .findFirst()
                        .orElse(null);

                if (image == null) {
                    image = new GetItemResponseDto.ItemImage();
                    image.setId(imageId);
                    image.setFileUrl(tuple.get(itemImageEntity.fileUrl.value));
                    image.setIsRepresent(tuple.get(itemImageEntity.isRepresent.value));
                    image.setLevel(tuple.get(itemImageEntity.level.value));
                    image.setItemImagePositions(new ArrayList<>()); // 위치 리스트 초기화
                    itemDto.getItemImages().add(image);
                }

                // 5. 이미지 위치 정보 추가
                if (tuple.get(itemImagePositionEntity.id) != null) {
                    GetItemResponseDto.ItemImage.ItemImagePosition position = new GetItemResponseDto.ItemImage.ItemImagePosition();
                    position.setId(tuple.get(itemImagePositionEntity.id));
                    position.setPositionX(tuple.get(itemImagePositionEntity.position.positionX));
                    position.setPositionY(tuple.get(itemImagePositionEntity.position.positionY));
                    position.setPositionZ(tuple.get(itemImagePositionEntity.position.positionZ));
                    position.setLevel(tuple.get(itemImagePositionEntity.level));
                    image.getItemImagePositions().add(position);
                }
            }
        }

        // 6. 리스트 변환 후 페이징 적용
        List<GetItemResponseDto> itemList = new ArrayList<>(itemMap.values());
        Page<GetItemResponseDto> pageResult = PageableExecutionUtils.getPage(itemList, pageable, countQuery::fetchCount);

        return toPaginationModel(pageResult);
    }

    @Override
    public List<GetItemOrderGachaResponseDto> findByGachaType(GachaType gachaType, List<Long> myInventory, GetMyRecreationAvatarResponseDto avatarInfo) {
        Set<ItemGrade> itemGrades = gachaType.getItemGradeMap().keySet();

        List<Tuple> results = queryFactory
                .select(
                        itemEntity.id,
                        itemEntity.itemName.itemName,
                        itemEntity.itemDescription.description,
                        itemEntity.price.value,
                        itemEntity.needLevel.value,
                        itemEntity.sortSeq.value,
                        itemEntity.category.id,
                        itemEntity.category.categoryName.categoryName,
                        itemEntity.itemGrade,
                        itemEntity.thumbnailUrl.value
                )
                .from(itemEntity)
                .where(itemEntity.itemGrade.in(itemGrades)
                        .and(itemEntity.id.notIn(myInventory))
                        .and(itemEntity.relatedCharacter.isNull()
                                .or(itemEntity.relatedCharacter.id.eq(avatarInfo.getCharacterNo()))
                        )
                        .and(itemEntity.needLevel.value.loe(avatarInfo.getLevel()))
                        .and(itemEntity.category.id.ne(CHARACTER_ID))
                )
                .fetch();

        List<Long> idList = results.stream().map(i -> i.get(itemEntity.id)).toList();

        List<Tuple> joinResults = queryFactory
                .select(
                        itemEntity.id,
                        itemEntity.itemName.itemName,
                        itemEntity.itemDescription.description,
                        itemEntity.price.value,
                        itemEntity.needLevel.value,
                        itemEntity.sortSeq.value,
                        itemEntity.category.id,
                        itemEntity.category.categoryName.categoryName,
                        itemEntity.itemGrade,
                        itemEntity.thumbnailUrl.value,

                        itemImageEntity.id,
                        itemImageEntity.fileUrl.value,
                        itemImageEntity.isRepresent.value,
                        itemImageEntity.level.value
                )
                .from(itemEntity)
                .leftJoin(itemImageEntity).on(itemEntity.id.eq(itemImageEntity.item.id))
                .where(itemEntity.id.in(idList))
                .fetch();

        // 3. 결과를 Map<Long, GetItemPageResponseDto> 형태로 변환
        Map<Long, GetItemOrderGachaResponseDto> itemMap = new LinkedHashMap<>();


        for (Tuple tuple : joinResults) {
            Long itemId = tuple.get(itemEntity.id);
            GetItemOrderGachaResponseDto itemDto = itemMap.get(itemId);

            // 아이템이 처음 추가될 때만 생성
            if (itemDto == null) {
                itemDto = new GetItemOrderGachaResponseDto();
                itemDto.setId(itemId);
                itemDto.setItemName(tuple.get(itemEntity.itemName.itemName));
                itemDto.setItemDescription(tuple.get(itemEntity.itemDescription.description));
                itemDto.setPrice(tuple.get(itemEntity.price.value));
                itemDto.setNeedLevel(tuple.get(itemEntity.needLevel.value));
                itemDto.setSortSeq(tuple.get(itemEntity.sortSeq.value));
                itemDto.setCategoryId(tuple.get(itemEntity.category.id));
                itemDto.setItemGrade(tuple.get(itemEntity.itemGrade));
                itemDto.setThumbnailUrl(tuple.get(itemEntity.thumbnailUrl.value));
                itemDto.setItemImages(new ArrayList<>()); // 이미지 리스트 초기화
                itemMap.put(itemId, itemDto);
            }

            // 4. 이미지 정보 추가
            if (tuple.get(itemImageEntity.id) != null) {
                Long imageId = tuple.get(itemImageEntity.id);
                GetItemOrderGachaResponseDto.ItemImage image = itemDto.getItemImages().stream()
                        .filter(img -> img.getId().equals(imageId))
                        .findFirst()
                        .orElse(null);

                if (image == null) {
                    image = new GetItemOrderGachaResponseDto.ItemImage();
                    image.setId(imageId);
                    image.setFileUrl(tuple.get(itemImageEntity.fileUrl.value));
                    image.setIsRepresent(tuple.get(itemImageEntity.isRepresent.value));
                    image.setLevel(tuple.get(itemImageEntity.level.value));
                    itemDto.getItemImages().add(image);
                }
            }
        }

        // 6. 리스트 변환 후 페이징 적용
        List<GetItemOrderGachaResponseDto> itemList = new ArrayList<>(itemMap.values());

        return itemList;
    }

    @Override
    public Long countPossibleGacha(GachaType gachaType, List<Long> myInventory, GetMyRecreationAvatarResponseDto avatarInfo) {
        Set<ItemGrade> itemGrades = gachaType.getItemGradeMap().keySet();

        return queryFactory
                .select(
                        itemEntity.count()
                )
                .from(itemEntity)
                .where(itemEntity.itemGrade.in(itemGrades)
                        .and(itemEntity.id.notIn(myInventory))
                        .and(itemEntity.relatedCharacter.isNull()
                                .or(itemEntity.relatedCharacter.id.eq(avatarInfo.getCharacterNo()))
                        )
                        .and(itemEntity.needLevel.value.loe(avatarInfo.getLevel()))
                        .and(itemEntity.category.id.ne(CHARACTER_ID))
                )
                .fetchOne();
    }

    @Override
    public List<String> findByGachaTypeIamgeList(GachaType gachaType) {

        Set<ItemGrade> itemGrades = gachaType.getItemGradeMap().keySet();
        List<String> imageList = new ArrayList<>();

        for (ItemGrade itemGrade : itemGrades) {
            List<String> results = queryFactory
                    .select(
                            itemImageEntity.fileUrl.value
                    )
                    .from(itemEntity)
                    .leftJoin(itemImageEntity).on(itemEntity.id.eq(itemImageEntity.item.id))
                    .where(itemEntity.itemGrade.eq(itemGrade)
                            .and(itemEntity.category.id.ne(CHARACTER_ID))
                    )
                    .orderBy(Expressions.numberTemplate(Double.class, "RAND()").asc())
                    .limit(3)
                    .fetch();

            imageList.addAll(results);

        }
        return imageList;
    }

}
