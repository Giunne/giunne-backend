package com.giunne.itemservice.domain.store.appliaction;

import com.giunne.itemservice.domain.store.appliaction.dto.reqeuest.CreateStoreRequestDto;
import com.giunne.itemservice.domain.store.appliaction.dto.response.CreateStoreResponseDto;
import com.giunne.itemservice.domain.store.appliaction.interfaces.StoreRepository;
import com.giunne.itemservice.domain.store.domain.Store;
import com.giunne.itemservice.domain.store.domain.type.StoreName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public CreateStoreResponseDto createStore(CreateStoreRequestDto dto) {
        Store store = Store.builder()
                .storeName(StoreName.from(dto.storeName()))
                .build();

        Store createStore = storeRepository.createStore(store);
        return CreateStoreResponseDto.builder()
                .id(createStore.getId())
                .storeName(createStore.getStoreName().getStoreName())
                .build();
    }

}
