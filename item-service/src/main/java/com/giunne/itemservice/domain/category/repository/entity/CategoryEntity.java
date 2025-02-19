package com.giunne.itemservice.domain.category.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.category.domain.Category;
import com.giunne.itemservice.domain.category.domain.type.CategoryName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long id;

    @Embedded
    private CategoryName categoryName;

    @Embedded
    private Active isActive = Active.from(true);

    public CategoryEntity(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
    }

    public Category toCategory() {
        return Category.builder()
                .id(id)
                .categoryName(categoryName)
                .build();
    }

    public Category toCategory(boolean isRoot, boolean isLeaf) {
        return Category.builder()
                .id(id)
                .categoryName(categoryName)
                .isRoot(isRoot)
                .isLeaf(isLeaf)
                .build();
    }
}
