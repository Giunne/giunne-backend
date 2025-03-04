package com.giunne.itemservice.domain.gacha.application;

import com.giunne.itemservice.domain.item.application.interfaces.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeightedRandomService {

    private final ItemRepository itemRepository;

}
