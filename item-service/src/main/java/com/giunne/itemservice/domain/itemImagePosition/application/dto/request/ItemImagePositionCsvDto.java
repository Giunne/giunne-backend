package com.giunne.itemservice.domain.itemImagePosition.application.dto.request;

import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.itemImagePosition.domain.ItemImagePosition;
import com.giunne.itemservice.domain.itemImagePosition.domain.type.Position;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemImagePositionCsvDto {
    @CsvBindByName(column = "level")
    private Long level;
    @CsvBindByName(column = "itemName")
    private String itemName;
    @CsvBindByName(column = "positionX")
    private Double positionX;
    @CsvBindByName(column = "positionY")
    private Double positionY;
    @CsvBindByName(column = "itemId")
    private Long itemId;

    public ItemImagePosition toItemImagePosition() {
        return ItemImagePosition.builder()
                .level(level)
                .position(Position.builder()
                        .positionX(positionX)
                        .positionY(positionY)
                        .positionZ(0.0)
                        .build())
                .item(Item.builder()
                        .id(itemId)
                        .build())
                .build();
    }

}
