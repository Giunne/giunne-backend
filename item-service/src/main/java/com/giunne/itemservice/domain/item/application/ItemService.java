package com.giunne.itemservice.domain.item.application;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.itemservice.domain.item.application.dto.request.GetItemPageRequestDto;
import com.giunne.itemservice.domain.item.application.dto.response.GetItemPageResponseDto;
import com.giunne.itemservice.domain.item.application.interfaces.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public PaginationModel<GetItemPageResponseDto> findByCategory(GetItemPageRequestDto dto) {
        return itemRepository.findByCategory(dto);
    }

}
