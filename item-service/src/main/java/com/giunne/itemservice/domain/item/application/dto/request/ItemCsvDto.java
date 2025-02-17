package com.giunne.itemservice.domain.item.application.dto.request;

import com.giunne.itemservice.domain.category.domain.Category;
import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.item.domain.type.*;
import com.giunne.itemservice.domain.itemImage.domain.ItemImage;
import com.giunne.itemservice.domain.itemImage.domain.type.FileName;
import com.giunne.itemservice.domain.itemImage.domain.type.FileSize;
import com.giunne.itemservice.domain.itemImage.domain.type.FileUrl;
import com.giunne.itemservice.domain.itemImage.domain.type.IsRepresent;
import com.giunne.itemservice.domain.store.domain.Store;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemCsvDto {

    @CsvBindByName(column = "itemName")
    private String itemName;
    @CsvBindByName(column = "itemUrl")
    private String itemUrl;
    @CsvBindByName(column = "needLevel")
    private Long needLevel;
    @CsvBindByName(column = "categoryId")
    private Long categoryId;
    @CsvBindByName(column = "storeId")
    private Long storeId;


    public Item toItem(){
        return Item.builder()
                .itemName(ItemName.from(itemName))
                .itemDescription(ItemDescription.from(""))
                .price(Price.from(100L))
                .needLevel(NeedLevel.from(needLevel))
                .sortSeq(SortSeq.from(0L))
                .stock(Stock.from(9999999999L))
                .category(Category.builder()
                        .id(categoryId)
                        .build())
                .store(Store.builder()
                        .id(storeId)
                        .build())
                .itemGrade(ItemGrade.B)
                .build();
    }

}
