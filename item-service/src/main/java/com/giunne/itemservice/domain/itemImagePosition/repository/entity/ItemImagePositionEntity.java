package com.giunne.itemservice.domain.itemImagePosition.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.itemImage.repositoy.entity.ItemImageEntity;
import com.giunne.itemservice.domain.itemImagePosition.domain.ItemImagePosition;
import com.giunne.itemservice.domain.itemImagePosition.domain.type.Position;
import com.giunne.itemservice.domain.option.repositoty.entity.OptionEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_image_position")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImagePositionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_position_no")
    private Long id;

    @Embedded
    private Position position; // 위치

    @Column(name = "item_no")
    private Long avatarNo; // 캐릭터 번호

    @Column(name = "level")
    private Long level; // 캐릭터 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_image_no")
    private ItemImageEntity itemImage;

    @Embedded
    private Active isActive = Active.from(true);

    public ItemImagePositionEntity(ItemImagePosition itemImagePosition) {
        this.id = itemImagePosition.getId();
        this.position = itemImagePosition.getPosition();
        this.level = itemImagePosition.getLevel();
        this.avatarNo = itemImagePosition.getAvatarNo();
        this.itemImage = new ItemImageEntity(itemImagePosition.getItemImage());
    }

    public ItemImagePosition toItemImagePosition() {
        return ItemImagePosition.builder()
                .id(id)
                .position(position)
                .level(level)
                .avatarNo(avatarNo)
                .build();
    }

}
