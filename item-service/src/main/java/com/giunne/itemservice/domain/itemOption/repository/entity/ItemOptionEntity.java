package com.giunne.itemservice.domain.itemOption.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.item.repository.entity.ItemEntity;
import com.giunne.itemservice.domain.itemOption.domain.type.Price;
import com.giunne.itemservice.domain.itemOption.domain.type.SortSeq;
import com.giunne.itemservice.domain.option.repositoty.entity.OptionEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "itemOption")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemOptionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_option_no")
    private Long id; // 아이템 프로필 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_no")
    private OptionEntity option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_no")
    private ItemEntity item;

    @Embedded
    private SortSeq sortSeq;

    @Embedded
    private Price price; // 기격

    @Embedded
    private Active isActive = Active.from(true);

}
