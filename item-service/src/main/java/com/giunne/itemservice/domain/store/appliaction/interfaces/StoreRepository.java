package com.giunne.itemservice.domain.store.appliaction.interfaces;

import com.giunne.itemservice.domain.store.domain.Store;

public interface StoreRepository {
    Store createStore(Store store);
}
