package com.giunne.itemservice.domain.store.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.itemservice.domain.store.domain.Store;
import com.giunne.itemservice.domain.store.domain.type.StoreName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_no")
    private Long id; // 상점 번호

    @Embedded
    private StoreName storeName; // 상점명

//    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Set<ItemEntity> itemEntities;

    @Embedded
    private Active isActive = Active.from(true);

    public StoreEntity(Store store){
        this.id = store.getId();
        this.storeName = store.getStoreName();
    }

    public Store toStore(){
        return Store.builder()
                .id(id)
                .storeName(storeName)
                .build();
    }
}
