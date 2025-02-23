package com.giunne.memberservice.domain.inventory.repository.entity;


import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.memberservice.domain.avatar.repository.entity.AvatarEntity;
import com.giunne.memberservice.domain.inventory.domain.Inventory;
import com.giunne.memberservice.domain.inventory.domain.type.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_no")
    private Long id; // 인벤토리 번호

    @Embedded
    private Quantity quantity; // 수량

    @Embedded
    private SortSeq sortSeq; // 정렬순서

    @Embedded
    private IsWear isWear; // 착용유무

    @Embedded
    private HasItem hasItem; // 보유유무

    @Embedded
    private ItemInfo itemInfo; // 아이템번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_no")
    private AvatarEntity avatar; // 캐릭터

    @Embedded
    private Active isActive = Active.from(true);

    public InventoryEntity(Inventory inventory) {
        this.id = inventory.getId();
        this.quantity = inventory.getQuantity();
        this.sortSeq = inventory.getSortSeq();
        this.isWear = inventory.getIsWear();
        this.hasItem = inventory.getHasItem();
        this.itemInfo = inventory.getItemInfo();
        this.avatar = new AvatarEntity(inventory.getAvatar());
    }

    public Inventory toInventory() {
        return Inventory.builder()
                .id(id)
                .quantity(quantity)
                .sortSeq(sortSeq)
                .isWear(isWear)
                .hasItem(hasItem)
                .itemInfo(itemInfo)
                .avatar(avatar.toAvatar())
                .build();
    }

}
