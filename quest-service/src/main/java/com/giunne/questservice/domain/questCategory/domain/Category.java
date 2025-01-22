package com.giunne.questservice.domain.questCategory.domain;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.quest.api.domain.Quest;
import com.giunne.questservice.domain.questCategory.domain.type.CategoryName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long id; // 퀘스트 카테고리 번호

    @Embedded
    private CategoryName categoryName; // 카테고리명

    // 최상위 부모 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_no")
    private Category root;

    // 부모 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_no")
    private Category parent;

    // 자식 정의
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Quest> quests = new HashSet<>();

    @Embedded
    private Active isActive = Active.from(true);
}
