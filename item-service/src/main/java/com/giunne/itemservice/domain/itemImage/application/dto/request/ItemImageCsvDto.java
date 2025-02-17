package com.giunne.itemservice.domain.itemImage.application.dto.request;

import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.itemImage.domain.ItemImage;
import com.giunne.itemservice.domain.itemImage.domain.type.FileName;
import com.giunne.itemservice.domain.itemImage.domain.type.FileSize;
import com.giunne.itemservice.domain.itemImage.domain.type.FileUrl;
import com.giunne.itemservice.domain.itemImage.domain.type.IsRepresent;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemImageCsvDto {
    @CsvBindByName(column = "itemId")
    private Long itemId;
    @CsvBindByName(column = "itemUrl")
    private String itemUrl;
    @CsvBindByName(column = "itemName")
    private String itemName;

    public ItemImage toItemImage() {
        return ItemImage.builder()
                .fileUrl(FileUrl.from(itemUrl))
                .fileName(FileName.from(itemName))
                .fileSize(FileSize.from(1L))
                .item(Item.builder()
                        .id(itemId)
                        .build())
                .isRepresent(IsRepresent.from(true))
                .build();
    }

}
