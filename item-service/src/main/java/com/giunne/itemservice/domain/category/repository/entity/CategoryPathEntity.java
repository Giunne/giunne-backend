package com.giunne.itemservice.domain.category.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.itemservice.domain.category.domain.CategoryPath;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category_path",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"parents_id", "child_id"})
        }
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryPathEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_path_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parents_id")
    private CategoryEntity parents;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private CategoryEntity child;

    @Embedded
    private Active isActive = Active.from(true);

    public CategoryPathEntity(CategoryPath categoryPath){
        this.id = categoryPath.getId();
        this.parents = new CategoryEntity(categoryPath.getParents());
        this.child = new CategoryEntity(categoryPath.getChild());
    }

    public CategoryPath toCategoryPath(){
        return CategoryPath.builder()
                .id(id)
                .parents(parents.toCategory())
                .child(child.toCategory())
                .build();
    }

}
