package com.giunne.itemservice.domain.optionCategory.repositoty.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.item.repository.entity.ItemEntity;
import com.giunne.itemservice.domain.optionCategory.domain.type.OptionCategoryName;
import com.giunne.itemservice.domain.optionCategory.domain.type.SortSeq;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "option_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionCategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_category_no")
    private Long id;

    @Embedded
    private OptionCategoryName name;

    @Embedded
    private SortSeq sortSeq; // 순서번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_no")
    private ItemEntity item;

    @Embedded
    private Active isActive = Active.from(true);
}
