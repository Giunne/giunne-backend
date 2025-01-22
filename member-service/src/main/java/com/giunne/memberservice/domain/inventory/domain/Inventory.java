package com.giunne.memberservice.domain.inventory.domain;


import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.memberservice.domain.player.domain.Player;
import com.giunne.memberservice.domain.inventory.domain.type.IsWear;
import com.giunne.memberservice.domain.inventory.domain.type.Quantity;
import com.giunne.memberservice.domain.inventory.domain.type.HasItem;
import com.giunne.memberservice.domain.inventory.domain.type.SortSeq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory extends BaseEntity {

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

    @Column(name = "item_no")
    private Long itemNo; // 아이템번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_no")
    private Player player; // 캐릭터

}
