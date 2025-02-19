package com.giunne.itemservice.domain.itemImagePosition.application;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import com.giunne.itemservice.domain.item.application.interfaces.ItemRepository;
import com.giunne.itemservice.domain.itemImage.application.interfaces.ItemImageRepository;
import com.giunne.itemservice.domain.itemImage.domain.ItemImage;
import com.giunne.itemservice.domain.itemImagePosition.application.dto.request.ItemImagePositionCsvDto;
import com.giunne.itemservice.domain.itemImagePosition.application.interfaces.ItemImagePositionRepository;
import com.giunne.itemservice.domain.itemImagePosition.domain.ItemImagePosition;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemImagePositionCsvService {
    private final int BATCH_SIZE = 1000;
    private final ItemImagePositionRepository itemImagePositionRepository;
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

    @Transactional
    public void itemImagePositionCsvToEntity(MultipartFile file) {
        if (!StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), ".csv")) {
            throw new BusinessException(ErrorCode.INVALID_CSV);
        }
        try {

            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

            CsvToBean<ItemImagePositionCsvDto> csvToBean = new CsvToBeanBuilder<ItemImagePositionCsvDto>(reader)
                    .withType(ItemImagePositionCsvDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<ItemImagePositionCsvDto> parse = csvToBean.parse();
            List<ItemImagePosition> itemCsvDtoList = parse.stream()
                    .map(ItemImagePositionCsvDto::toItemImagePosition)
                    .collect(Collectors.toList());

            int listSize = itemCsvDtoList.size();
            for (int i = 0; i < listSize; i += BATCH_SIZE) {
                int toIndex = Math.min(i + BATCH_SIZE, listSize);
                List<ItemImagePosition> sublist = itemCsvDtoList.subList(i, toIndex);
                processBatch(sublist);
            }

        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_FORMAT);
        }
    }

    private void processBatch(List<ItemImagePosition> itemImagePositionList) {
        List<ItemImagePosition> newItemImagePositions = new ArrayList<>();

        itemImagePositionList.forEach(imagePosition -> {
            List<ItemImage> imageList = itemImageRepository.findByItemId(
                    imagePosition.getItem().getId()
            );

            if (imageList.isEmpty()) {
                return;
            }

            imagePosition.changItemImage(imageList.get(0));

            for (int i = 1; i < imageList.size(); i++) {
                ItemImagePosition newImagePosition = ItemImagePosition.builder()
                        .id(null)
                        .position(imagePosition.getPosition())
                        .itemImage(imageList.get(i))
                        .level(imagePosition.getLevel())
                        .item(imagePosition.getItem())
                        .build();
                newItemImagePositions.add(newImagePosition);
            }
        });

        // 새롭게 생성된 객체를 기존 리스트에 추가
        itemImagePositionList.addAll(newItemImagePositions);
        itemImagePositionRepository.saveAll(itemImagePositionList);
    }
}
