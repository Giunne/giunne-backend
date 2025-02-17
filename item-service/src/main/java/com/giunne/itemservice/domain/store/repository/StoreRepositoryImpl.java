package com.giunne.itemservice.domain.store.repository;

import com.giunne.itemservice.domain.store.appliaction.interfaces.StoreRepository;
import com.giunne.itemservice.domain.store.domain.Store;
import com.giunne.itemservice.domain.store.repository.entity.StoreEntity;
import com.giunne.itemservice.domain.store.repository.jpa.JpaStoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {

    private final JpaStoreRepository jpaStoreRepository;

    @Override
    public Store createStore(Store store) {
        StoreEntity storeEntity = new StoreEntity(store);
        StoreEntity savedEntity = jpaStoreRepository.save(storeEntity);

        return savedEntity.toStore();
    }
}
