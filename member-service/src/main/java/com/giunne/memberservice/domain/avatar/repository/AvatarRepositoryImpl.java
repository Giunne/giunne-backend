package com.giunne.memberservice.domain.avatar.repository;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetWearingItemResponseDto;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.avatar.application.dto.AvatarWithWearingItemResponseDto;
import com.giunne.memberservice.domain.avatar.application.interfaces.AvatarRepository;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.avatar.repository.entity.AvatarEntity;
import com.giunne.memberservice.domain.avatar.repository.entity.QAvatarEntity;
import com.giunne.memberservice.domain.avatar.repository.jpa.JpaAvatarRepository;
import com.giunne.memberservice.domain.inventory.repository.entity.QInventoryEntity;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.member.repository.entity.QMemberEntity;
import com.giunne.memberservice.domain.recreation.repository.entity.QRecreationEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@AllArgsConstructor
public class AvatarRepositoryImpl implements AvatarRepository {

    private final JpaAvatarRepository jpaAvatarRepository;
    private final JPAQueryFactory queryFactory;
    private static final QMemberEntity memberEntity = QMemberEntity.memberEntity;
    private static final QAvatarEntity avatarEntity = QAvatarEntity.avatarEntity;
    private static final QInventoryEntity inventoryEntity = QInventoryEntity.inventoryEntity;
    private static final QRecreationEntity recreationEntity = QRecreationEntity.recreationEntity;

    @Override
    @Transactional
    public Avatar createAvatar(Avatar avatar){
        AvatarEntity avatarEntity = new AvatarEntity(avatar);
        AvatarEntity entity = jpaAvatarRepository.save(avatarEntity);
        return entity.toAvatar();
    }

    public Avatar findById(Long avatarId){
        AvatarEntity entity = jpaAvatarRepository.findById(avatarId)
                .orElseThrow(IllegalArgumentException::new);
        return entity.toAvatar();
    }

    @Override
    public  List<AvatarWithWearingItemResponseDto>  getMyAvatarList(Member member) {

        List<Tuple> joinResults = queryFactory
                .select(
                        avatarEntity.id,
                        avatarEntity.nickname.nickname,
                        avatarEntity.recreation.id,
                        avatarEntity.exp.exp,
                        avatarEntity.level.level,
                        avatarEntity.point.point,
                        avatarEntity.characterNo,

                        recreationEntity.recreationName.recreationName,

                        inventoryEntity.itemInfo.itemNo,
                        inventoryEntity.itemInfo.itemName,
                        inventoryEntity.itemInfo.categoryNo
                )
                .from(avatarEntity)
                .join(inventoryEntity).on(inventoryEntity.avatar.id.eq(avatarEntity.id))
                .join(recreationEntity).on(recreationEntity.id.eq(avatarEntity.recreation.id))
                .where(
                        avatarEntity.member.id.eq(member.getId()),
                        inventoryEntity.isWear.isWear.eq(true)
                )
                .fetch();

        // 결과를 Map<Long, AvatarWithWearingItemResponseDto> 형태로 변환
        Map<Long, AvatarWithWearingItemResponseDto> avatarMap = new LinkedHashMap<>();
        for (Tuple tuple : joinResults) {
            Long avatarId = tuple.get(avatarEntity.id);
            AvatarWithWearingItemResponseDto avatarDto = avatarMap.get(avatarId);

            // 아이템이 처음 추가될 때만 생성
            if (avatarDto == null) {
                avatarDto =  AvatarWithWearingItemResponseDto.builder()
                        .id(avatarId)
                        .nickname(tuple.get(avatarEntity.nickname.nickname))
                        .recreationId(tuple.get(avatarEntity.recreation.id))
                        .recreationName(tuple.get(recreationEntity.recreationName.recreationName))
                        .exp(tuple.get(avatarEntity.exp.exp))
                        .level(tuple.get(avatarEntity.level.level))
                        .point(tuple.get(avatarEntity.point.point))
                        .characterNo(tuple.get(avatarEntity.characterNo))
                        .wearingItemIds(new ArrayList<>())
                        .build();
                ;
                avatarMap.put(avatarId, avatarDto);
            }

            // 아이템 id 추가
            if(tuple.get(inventoryEntity.itemInfo.itemNo) != null){
                Long itemId = tuple.get(inventoryEntity.itemInfo.itemNo);
                avatarDto.getWearingItemIds().add(itemId);
            }
        }

        return new ArrayList<>(avatarMap.values());
    }

}
