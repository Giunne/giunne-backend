package com.giunne.itemservice.domain.item.application;

import com.giunne.itemservice.domain.item.domain.type.GachaType;
import com.giunne.itemservice.domain.orders.application.dto.response.GachaTypeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    public List<GachaTypeResponseDto> getGachaTypes() {

        return Stream.of(GachaType.values())
                .map(GachaTypeResponseDto::new)
                .collect(Collectors.toList());
    }

}
