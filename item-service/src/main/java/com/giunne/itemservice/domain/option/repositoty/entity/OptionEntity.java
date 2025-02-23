package com.giunne.itemservice.domain.option.repositoty.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.option.domain.type.OptionName;
import com.giunne.itemservice.domain.option.domain.type.SortSeq;
import com.giunne.itemservice.domain.optionCategory.repositoty.entity.OptionCategoryEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_no")
    private Long id;

    @Embedded
    private OptionName name;

    @Embedded
    private SortSeq sortSeq; // 순서번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_category_no")
    private OptionCategoryEntity itemOptionGroup;

    @Embedded
    private Active isActive = Active.from(true);

}
