package com.giunne.itemservice.domain.item.application;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import com.giunne.itemservice.domain.item.application.dto.request.ItemCsvDto;
import com.giunne.itemservice.domain.item.application.interfaces.ItemRepository;
import com.giunne.itemservice.domain.item.domain.Item;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCsvService {
    private final int BATCH_SIZE = 1000;
    private final ItemRepository itemRepository;

    @Transactional
    public void itemCsvToEntity(MultipartFile file) {
        if (!StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), ".csv")) {
            throw new BusinessException(ErrorCode.INVALID_CSV);
        }

        try {

            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

            CsvToBean<ItemCsvDto> csvToBean = new CsvToBeanBuilder<ItemCsvDto>(reader)
                    .withType(ItemCsvDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<ItemCsvDto> parse = csvToBean.parse();
            List<Item> itemCsvDtoList = parse.stream()
                    .map(ItemCsvDto::toItem)
                    .collect(Collectors.toList());

            int listSize = itemCsvDtoList.size();
            for (int i = 0; i < listSize; i += BATCH_SIZE) {
                int toIndex = Math.min(i + BATCH_SIZE, listSize);
                List<Item> sublist = itemCsvDtoList.subList(i, toIndex);
                processBatch(sublist);
            }

        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_FORMAT);
        }
    }

    private void processBatch(List<Item> itemList) {
        itemRepository.saveAll(itemList);
    }

}
