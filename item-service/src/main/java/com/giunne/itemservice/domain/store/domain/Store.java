package com.giunne.itemservice.domain.store.domain;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.store.domain.type.StoreName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "store")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_no")
    private Long id; // 상점 번호

    @Embedded
    private StoreName storeName; // 상점명

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Item> items;

    @Embedded
    private Active isActive = Active.from(true);
}
